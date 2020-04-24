package com.mvshyvk.kaldi.service.api;

import com.mvshyvk.kaldi.service.api.*;
import com.mvshyvk.kaldi.service.models.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.mvshyvk.kaldi.service.models.Result;
import com.mvshyvk.kaldi.service.models.TaskId;

import java.util.Map;
import java.util.List;
import com.mvshyvk.kaldi.service.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2020-04-23T21:31:33.644Z[GMT]")public abstract class TaskApiService {
    public abstract Response taskPost(Object body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response taskTaskIdStatusGet(String taskId,SecurityContext securityContext) throws NotFoundException;
}
