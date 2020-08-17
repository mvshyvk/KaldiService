package com.mvshyvk.kaldi.service.webapp;

import com.mvshyvk.kaldi.service.webapp.kaldiConnector.KaldiConnector;
import com.mvshyvk.kaldi.service.webapp.kaldiConnector.KaldiSimulator;
import com.mvshyvk.kaldi.service.webapp.status.ServiceStatusProvider;
import com.mvshyvk.kaldi.service.webapp.status.ServiceStatusProviderImpl;
import com.mvshyvk.kaldi.service.webapp.task.CapacitiesService;
import com.mvshyvk.kaldi.service.webapp.task.TaskHandlerService;
import com.mvshyvk.kaldi.service.webapp.task.TaskHandlerServiceImpl;

/**
 * Factory class for creating objects for KaldiService 
 */
public  class KaldiServiceFactory {
	
	// Singleton instance of united TaskHandlerService and CapacitiesService implementation 
	private static TaskHandlerServiceImpl taskHandlerService;

	/**
	 * Creates singleton instance of task handling service
	 */
	public static TaskHandlerService createTaskHandlerService() {
		
		initTaskHandlerServiceSingleton();
		return taskHandlerService;
	}
	
	/**
	 * Creates service that provides KaldiService status in presentation format 
	 */
	public static ServiceStatusProvider createServiceStatusProvider() {
		
		return new ServiceStatusProviderImpl(createCapacitiesService());
	}
	
	/**
	 * Creates simulator of KaldiConnector service interface 
	 */
	public static KaldiConnector createKaldiConnectorSimulator() {
		
		return new KaldiSimulator();
	}
	
	/** 
	 * Creates singleton instance of capacities service
	 */
	private static CapacitiesService createCapacitiesService() {
		
		initTaskHandlerServiceSingleton();
		return taskHandlerService;
	}
	
	/**
	 * Initialize singleton instance of service
	 */
	private static void initTaskHandlerServiceSingleton() {
		
		if (taskHandlerService == null) {
			taskHandlerService = new TaskHandlerServiceImpl();
		}
	}
	
}
