package com.mvshyvk.kaldi.service.webapp.task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.mvshyvk.kaldi.service.models.TaskId;
import com.mvshyvk.kaldi.service.webapp.exception.ProcessingQueueFull;
import com.mvshyvk.kaldi.service.webapp.kaldiConnector.KaldiConnector;
import com.mvshyvk.kaldi.service.webapp.kaldiConnector.KaldiSimulator;

// TODO: Extract interface and use interface
public class TaskHandler {
	
	// TODO: Depth of processing queue is hard-coded at the moment
	private final int queueCapacity = 8;
	
	private Map<String, TaskData> completedTasks = new HashMap<String, TaskData>();
	private Map<String, TaskData> inProgressTasks = new HashMap<String, TaskData>();
	private BlockingQueue<TaskData> processingQueue;
	
	private ExecutorService executorService; 
	private int workersCount = 0;
	
	/**
	 * Constructor
	 */
	public TaskHandler() {
		
		processingQueue = new ArrayBlockingQueue<TaskData>(queueCapacity);
		
		executorService = Executors.newCachedThreadPool();
		addWorker();
		addWorker();
		
		// TODO: Need to consider correct shutdown of executorService during shutting down webapp
	}

	private void addWorker() {
		
		// TODO: Use factory 
		executorService.submit(new TaskExecutor(new KaldiSimulator(), processingQueue));
		workersCount++;
	}

	/**
	 * Adds data for processing 
	 * 
	 * @param data - data for processing
	 * @return identifier of the task added to queue
	 * @throws ProcessingQueueFull - in case if queue is full
	 */
	public TaskId postTask(byte[] data) throws ProcessingQueueFull {
		
		TaskData task = new TaskData(data);
		
		if (processingQueue.offer(task)) {
			return new TaskId().taskId(task.getTaskId());
		}
		
		throw new ProcessingQueueFull("Task wasn't posted for processing because queue is full");				
	}
	
	public int getQueueCapacity() {
		return queueCapacity;
	}
	
	public int getQueueAvailableCapacity() {
		return processingQueue.remainingCapacity();
	}
	
	public int getWorkersCount() {
		return workersCount;
	}
	
	/**
	 * Returns task status
	 * 
	 * @param taskId identifier of task to retrieve status
	 * @return status of task
	 */
	public TaskStatus getTaskStatus(TaskId taskId) {
		
		if (processingQueue.stream().anyMatch(item -> item.getTaskId().equals(taskId.getTaskId()))) {
			return TaskStatus.enInQueue;
		}
		
		if (inProgressTasks.containsKey(taskId.getTaskId())) {
			return TaskStatus.enInProgress;
		}
		
		if (completedTasks.containsKey(taskId.getTaskId())) {
			return TaskStatus.enCompleted;
		}
		
		return TaskStatus.enUnknown;		
	}

	public TaskData getTaskData(TaskId taskId) {		
		return completedTasks.get(taskId.getTaskId());
	}
	
	
	class TaskExecutor implements Runnable
	{
		private final int waitingTimeout = 1;
		
		private KaldiConnector ipcConnector;
		private BlockingQueue<TaskData> tasksQueue;
		
		
		public TaskExecutor(KaldiConnector ipcConnector, BlockingQueue<TaskData> queue) {
			
			this.ipcConnector = ipcConnector;
			this.tasksQueue = queue;
		}

		@Override
		public void run() {
			
			while (!Thread.currentThread().isInterrupted()) {
				try {
					
					TaskData task  = tasksQueue.poll(waitingTimeout, TimeUnit.SECONDS);
					if (task != null) {
						executeTask(task);
					}
				}
				catch (InterruptedException e) {
					
					Thread.currentThread().interrupt();
					e.printStackTrace();
				}
			}
		}

		private void executeTask(TaskData task) throws InterruptedException {
			
			inProgressTasks.put(task.getTaskId(), task);
			try {
				ipcConnector.processSpeach(task);
			}
			finally {
				task.minimizeMemoryAllocation();
				completedTasks.put(task.getTaskId(), task);
				inProgressTasks.remove(task.getTaskId());
			}
		}
		
	}

}
