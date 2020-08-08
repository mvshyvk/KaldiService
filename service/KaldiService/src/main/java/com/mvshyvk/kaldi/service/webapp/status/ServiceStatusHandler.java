package com.mvshyvk.kaldi.service.webapp.status;

import com.mvshyvk.kaldi.service.models.ServiceStatus;

/**
 * Class that handles service capacities and current status
 */
public class ServiceStatusHandler {
	
	private ServiceStatus serviceStatus;
	
	public ServiceStatusHandler() {
		
		serviceStatus = retrieveServiceStatus();
	}
	
	public ServiceStatus getServiceStatus() {
		
		return serviceStatus;
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
		// TODO: Improve algorithm of queue depth calculation
		return 4 + workersCount * 4;
	}

	private int loadWorkersCount() {
		// TODO: Need to load count of available workers
		// Currently hard-coded
		return 1;
	}

}
