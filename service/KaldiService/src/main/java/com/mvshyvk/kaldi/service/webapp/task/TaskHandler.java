package com.mvshyvk.kaldi.service.webapp.task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import com.mvshyvk.kaldi.service.models.TaskId;
import com.mvshyvk.kaldi.service.webapp.exception.ProcessingQueueFull;

public class TaskHandler {
	
	// TODO: Depth of processing queue is hard-coded at the moment
	private final int queueCapacity = 8;
	
	private Map<String, TaskData> completedTasks = new HashMap<String, TaskData>();
	private BlockingQueue<TaskData> processingQueue;
	
	/**
	 * Constructor
	 */
	public TaskHandler() {
		
		processingQueue = new ArrayBlockingQueue<TaskData>(queueCapacity);
	}
	
	// TODO: Update signature of the method
	public TaskId postTask() throws ProcessingQueueFull {
		
		TaskData task = new TaskData();
		
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

}
