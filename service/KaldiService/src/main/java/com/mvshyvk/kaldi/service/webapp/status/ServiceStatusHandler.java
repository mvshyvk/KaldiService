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
		
		int workersCount = loadWorkersCount();
		status.setWorkersCount(workersCount);
		
		int queueDepth = calculateQueueDepth(workersCount);
		status.setQueueDepth(queueDepth);
		status.setAvailableQueueSlots(queueDepth);
		
		return status;
	}

	private int calculateQueueDepth(int workersCount) {		
		return taskHandler.getQueueCapacity();
	}

	private int loadWorkersCount() {
		// TODO: Need to load count of available workers
		// Currently hard-coded
		return 1;
	}

}
