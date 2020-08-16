package com.mvshyvk.kaldi.service.webapp.status;

import com.mvshyvk.kaldi.service.models.ServiceStatus;
import com.mvshyvk.kaldi.service.webapp.task.TaskHandler;

/**
 * Class that handles service capacities and current status
 */
public class ServiceStatusHandler {
	
	private ServiceStatus serviceStatus;
	private TaskHandler taskHandler;
	
	/**
	 * Constructor
	 */
	public ServiceStatusHandler(TaskHandler taskHandler) {
		
		this.taskHandler = taskHandler;
		serviceStatus = retrieveServiceStatus();
	}
	
	/**
	 * Gets global KaldiService status
	 */
	public ServiceStatus getServiceStatus() {

		// Algorithm is valid only in case if capacities don't change over time
		ServiceStatus status = new ServiceStatus(serviceStatus);
		status.setAvailableQueueSlots(taskHandler.getQueueAvailableCapacity());
		return status;
	}
	
	/**
	 * Retrieves initial service capacities 
	 */
	private ServiceStatus retrieveServiceStatus() {
		
		ServiceStatus status = new ServiceStatus();
		
		status.setWorkersCount(getWorkersCount());
		status.setQueueDepth(getQueueDepth());
		status.setAvailableQueueSlots(getQueueDepth());
		
		return status;
	}

	/**
	 * Gets processing queue depth  
	 */
	private int getQueueDepth() {
		return taskHandler.getQueueCapacity();
	}

	/**
	 * Gets workers count 
	 */
	private int getWorkersCount() {
		return taskHandler.getWorkersCount();
	}

}
