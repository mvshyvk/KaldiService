package com.mvshyvk.kaldi.service.webapp;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.mvshyvk.kaldi.service.webapp.status.ServiceStatusProvider;
import com.mvshyvk.kaldi.service.webapp.status.ServiceStatusProviderImpl;
import com.mvshyvk.kaldi.service.webapp.task.CapacitiesService;
import com.mvshyvk.kaldi.service.webapp.task.TaskHandlerService;
import com.mvshyvk.kaldi.service.webapp.task.TaskHandlerServiceImpl;

/**
 * Starting point of webapp context initialization
 */
public class KaldiServiceAppContext implements ServletContextListener {
	
	public static Logger log = Logger.getLogger(KaldiServiceAppContext.class);

	public static ServiceStatusProvider statusProvider;
	public static TaskHandlerService taskHandlerService;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		log.info("Starting KaldiService ...");

		// TODO: Introduce factory
		taskHandlerService = new TaskHandlerServiceImpl();
		statusProvider = new ServiceStatusProviderImpl((CapacitiesService)taskHandlerService);
		
		log.info("KaldiService started");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

		log.info("Stopping KaldiService ...");
		taskHandlerService.stopService();
		log.info("KaldiService stopped");
	}

}
