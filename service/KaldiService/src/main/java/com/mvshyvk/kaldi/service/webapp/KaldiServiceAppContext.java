package com.mvshyvk.kaldi.service.webapp;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mvshyvk.kaldi.service.webapp.status.ServiceStatusHandler;
import com.mvshyvk.kaldi.service.webapp.task.TaskHandler;

public class KaldiServiceAppContext implements ServletContextListener {
	
	public static ServiceStatusHandler serviceStatusHandler;
	public static TaskHandler taskHandler;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {

		taskHandler = new TaskHandler();
		serviceStatusHandler = new ServiceStatusHandler(taskHandler);
		sce.getServletContext().setAttribute(Constants.SERVICE_STATUS_CONTEXT_ATTRIBUTE, serviceStatusHandler);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
