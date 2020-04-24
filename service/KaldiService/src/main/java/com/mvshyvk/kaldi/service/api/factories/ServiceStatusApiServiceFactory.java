package com.mvshyvk.kaldi.service.api.factories;

import com.mvshyvk.kaldi.service.api.ServiceStatusApiService;
import com.mvshyvk.kaldi.service.api.impl.ServiceStatusApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2020-04-23T21:31:33.644Z[GMT]")
public class ServiceStatusApiServiceFactory {
	private final static ServiceStatusApiService service = new ServiceStatusApiServiceImpl();

	public static ServiceStatusApiService getServiceStatusApi() {
		return service;
	}
}
