package com.mvshyvk.kaldi.service.api.impl;

import com.mvshyvk.kaldi.service.api.*;
import com.mvshyvk.kaldi.service.api.NotFoundException;
import com.mvshyvk.kaldi.service.models.ServiceStatus;
import com.mvshyvk.kaldi.service.webapp.KaldiServiceAppContext;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2020-04-23T21:31:33.644Z[GMT]")
public class ServiceStatusApiServiceImpl extends ServiceStatusApiService {
	
	private static Logger log = Logger.getLogger(ServiceStatusApiServiceImpl.class);
			
	@Override
	public Response serviceStatusGet(SecurityContext securityContext) throws NotFoundException {
		
		ServiceStatus status = KaldiServiceAppContext.statusProvider.getServiceStatus();
		log.debug(String.format("GET serviceStatus: %s", status.toString()));
		
		return Response.ok().entity(status).build();
	}
}
