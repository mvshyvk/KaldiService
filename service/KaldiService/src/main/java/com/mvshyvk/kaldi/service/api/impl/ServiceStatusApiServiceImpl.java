package com.mvshyvk.kaldi.service.api.impl;

import com.mvshyvk.kaldi.service.api.*;
import com.mvshyvk.kaldi.service.models.*;

import com.mvshyvk.kaldi.service.models.ServiceStatus;

import java.util.Map;
import java.util.List;
import com.mvshyvk.kaldi.service.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2020-04-23T21:31:33.644Z[GMT]")public class ServiceStatusApiServiceImpl extends ServiceStatusApiService {
    @Override
    public Response serviceStatusGet(SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
}
