package com.mvshyvk.kaldi.service.webapp;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.mvshyvk.kaldi.service.webapp.status.ServiceStatusHandler;
import com.mvshyvk.kaldi.service.webapp.task.TaskHandler;

/**
 * Starting point of webapp context initialization
 */
public class KaldiServiceAppContext implements ServletContextListener {
	
	public static Logger log = Logger.getLogger(KaldiServiceAppContext.class);

	public static ServiceStatusHandler serviceStatusHandler;
	public static TaskHandler taskHandler;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		log.info("Starting KaldiService ...");

		taskHandler = new TaskHandler();
		serviceStatusHandler = new ServiceStatusHandler(taskHandler);
		
		log.info("KaldiService started");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

		log.info("Stopping KaldiService ...");
		log.info("KaldiService stopped");
	}

}
