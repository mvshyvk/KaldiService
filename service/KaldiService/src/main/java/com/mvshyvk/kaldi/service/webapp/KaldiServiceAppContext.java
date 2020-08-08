package com.mvshyvk.kaldi.service.webapp;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mvshyvk.kaldi.service.webapp.status.ServiceStatusHandler;

public class KaldiServiceAppContext implements ServletContextListener {
	
	public static ServiceStatusHandler serviceStatusHandler;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {

		serviceStatusHandler = new ServiceStatusHandler();
		sce.getServletContext().setAttribute(Constants.SERVICE_STATUS_CONTEXT_ATTRIBUTE, serviceStatusHandler);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
