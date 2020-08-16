package com.mvshyvk.kaldi.service.webapp.status;

import com.mvshyvk.kaldi.service.models.ServiceStatus;
import com.mvshyvk.kaldi.service.webapp.task.CapacitiesService;

/**
 * Service that provides info about KaldiService capacities and its current status
 */
public class ServiceStatusProviderImpl implements ServiceStatusProvider {
	
	private final CapacitiesService capacitiesService;

	/**
	 * Constructor
	 */
	public ServiceStatusProviderImpl(CapacitiesService capacitiesService) {

		this.capacitiesService = capacitiesService;
	}
	
	/**
	 * Gets global KaldiService status
	 */
	@Override
	public ServiceStatus getServiceStatus() {

		ServiceStatus status = new ServiceStatus();
		
		status.setWorkersCount(getWorkersCount());
		status.setQueueDepth(getQueueDepth());
		status.setAvailableQueueSlots(capacitiesService.getQueueAvailableCapacity());
		
		return status;
	}
	
	/**
	 * Gets processing queue depth  
	 */
	private int getQueueDepth() {
		return capacitiesService.getQueueCapacity();
	}

	/**
	 * Gets workers count 
	 */
	private int getWorkersCount() {
		return capacitiesService.getWorkersCount();
	}

}
