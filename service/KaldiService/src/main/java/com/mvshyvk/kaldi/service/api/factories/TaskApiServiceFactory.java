package com.mvshyvk.kaldi.service.api.factories;

import com.mvshyvk.kaldi.service.api.TaskApiService;
import com.mvshyvk.kaldi.service.api.impl.TaskApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2020-04-23T21:31:33.644Z[GMT]")
public class TaskApiServiceFactory {
	private final static TaskApiService service = new TaskApiServiceImpl();

	public static TaskApiService getTaskApi() {
		return service;
	}
}
