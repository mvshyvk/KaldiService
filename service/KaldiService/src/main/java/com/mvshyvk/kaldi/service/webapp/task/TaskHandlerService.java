package com.mvshyvk.kaldi.service.webapp.task;

import com.mvshyvk.kaldi.service.models.TaskId;
import com.mvshyvk.kaldi.service.webapp.exception.ProcessingQueueFull;

/**
 * Interface for submitting tasks with data for processing and for retrieving processing results 
 */
public interface TaskHandlerService {

	/**
	 * Adds data for processing 
	 * 
	 * @param data - data for processing
	 * @return identifier of the task added to queue
	 * @throws ProcessingQueueFull - in case if queue is full
	 */
	TaskId postTask(byte[] data) throws ProcessingQueueFull;

	/**
	 * Prepares service for shutting down webapp
	 */
	void stopService();

	/**
	 * Returns task data (results)
	 * 
	 * @param taskId - identifier of task to retrieve data
	 * @return task data (results)
	 */
	TaskData getTaskData(TaskId taskId);

	/**
	 * Returns task status
	 * 
	 * @param taskId - identifier of task to retrieve status
	 * @return status of task
	 */
	TaskStatus getTaskStatus(TaskId taskId);
	
}
