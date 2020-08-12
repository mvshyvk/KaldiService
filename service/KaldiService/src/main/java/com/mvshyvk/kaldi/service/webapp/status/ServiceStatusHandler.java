package com.mvshyvk.kaldi.service.webapp.status;

import com.mvshyvk.kaldi.service.models.ServiceStatus;
import com.mvshyvk.kaldi.service.webapp.task.TaskHandler;

/**
 * Class that handles service capacities and current status
 */
public class ServiceStatusHandler {
	
	private ServiceStatus serviceStatus;
	private TaskHandler taskHandler;
	
	public ServiceStatusHandler(TaskHandler taskHandler) {
		
		this.taskHandler = taskHandler;
		serviceStatus = retrieveServiceStatus();
	}
	
	public ServiceStatus getServiceStatus() {
		
		ServiceStatus status = new ServiceStatus(serviceStatus);
		status.setAvailableQueueSlots(taskHandler.getQueueAvailableCapacity());
		return status;
	}
	
	private ServiceStatus retrieveServiceStatus() {
		
		ServiceStatus status = new ServiceStatus();
		
		status.setWorkersCount(getWorkersCount());
		status.setQueueDepth(getQueueDepth());
		status.setAvailableQueueSlots(getQueueDepth());
		
		return status;
	}

	private int getQueueDepth() {
		return taskHandler.getQueueCapacity();
	}

	private int getWorkersCount() {
		return taskHandler.getWorkersCount();
	}

}
