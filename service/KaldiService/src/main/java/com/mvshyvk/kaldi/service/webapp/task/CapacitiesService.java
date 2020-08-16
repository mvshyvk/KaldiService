package com.mvshyvk.kaldi.service.webapp.task;

public interface CapacitiesService {
	
	/**
	 * Returns count of processing workers
	 */
	int getWorkersCount();

	/**
	 * Returns depth of processing queue
	 */
	int getQueueCapacity();

	/**
	 * Returns count of available slots in processing queue at the moment 
	 */
	int getQueueAvailableCapacity();

}
