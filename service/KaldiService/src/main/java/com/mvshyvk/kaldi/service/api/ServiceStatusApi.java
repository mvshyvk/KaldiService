package com.mvshyvk.kaldi.service.api;

import com.mvshyvk.kaldi.service.api.ServiceStatusApiService;
import com.mvshyvk.kaldi.service.api.factories.ServiceStatusApiServiceFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import com.mvshyvk.kaldi.service.models.ServiceStatus;

import com.mvshyvk.kaldi.service.api.NotFoundException;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;

@Path("/serviceStatus")

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2020-04-23T21:31:33.644Z[GMT]")
public class ServiceStatusApi {
	private final ServiceStatusApiService delegate;

	public ServiceStatusApi(@Context ServletConfig servletContext) {
		ServiceStatusApiService delegate = null;

		if (servletContext != null) {
			String implClass = servletContext.getInitParameter("ServiceStatusApi.implementation");
			if (implClass != null && !"".equals(implClass.trim())) {
				try {
					delegate = (ServiceStatusApiService) Class.forName(implClass).newInstance();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}

		if (delegate == null) {
			delegate = ServiceStatusApiServiceFactory.getServiceStatusApi();
		}

		this.delegate = delegate;
	}

	@GET

	@Produces({ "application/json" })
	@Operation(summary = "", description = "Returns status of Kaldi speach recognition service", security = {
			@SecurityRequirement(name = "password", scopes = { "" }) }, tags = { "Service" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Status of Kaldi speach recognition service", content = @Content(schema = @Schema(implementation = ServiceStatus.class))),

			@ApiResponse(responseCode = "401", description = "Unauthorized") })
	public Response serviceStatusGet(@Context SecurityContext securityContext) throws NotFoundException {
		return delegate.serviceStatusGet(securityContext);
	}
}
