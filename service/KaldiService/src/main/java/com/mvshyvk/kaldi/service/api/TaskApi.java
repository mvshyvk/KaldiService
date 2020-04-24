package com.mvshyvk.kaldi.service.api;

import com.mvshyvk.kaldi.service.api.TaskApiService;
import com.mvshyvk.kaldi.service.api.factories.TaskApiServiceFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import com.mvshyvk.kaldi.service.models.Result;
import com.mvshyvk.kaldi.service.models.TaskId;

import com.mvshyvk.kaldi.service.api.NotFoundException;

import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;


@Path("/task")


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2020-04-23T21:31:33.644Z[GMT]")public class TaskApi  {
   private final TaskApiService delegate;

   public TaskApi(@Context ServletConfig servletContext) {
      TaskApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("TaskApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (TaskApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = TaskApiServiceFactory.getTaskApi();
      }

      this.delegate = delegate;
   }

    @POST
    
    @Consumes({ "application/octet-stream" })
    @Produces({ "application/json" })
    @Operation(summary = "", description = "Adds a task for speach recognition into processing queue", security = {
        @SecurityRequirement(name = "password", scopes = {
            ""        })    }, tags={ "Task" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "202", description = "Task added to processing queue", content = @Content(schema = @Schema(implementation = TaskId.class))),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        
        @ApiResponse(responseCode = "429", description = "Processing queue is full") })
    public Response taskPost(@Parameter(in = ParameterIn.DEFAULT, description = "" ) InputStream inputStream

,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.taskPost(inputStream, securityContext);
    }
    @GET
    @Path("/{taskId}/status")
    
    @Produces({ "application/json" })
    @Operation(summary = "", description = "Retrieves task execution status", security = {
        @SecurityRequirement(name = "password", scopes = {
            ""        })    }, tags={ "Task" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Task has been completed, results are available in response", content = @Content(schema = @Schema(implementation = Result.class))),
        
        @ApiResponse(responseCode = "204", description = "Task is not processed yet"),
        
        @ApiResponse(responseCode = "401", description = "Unauthorized") })
    public Response taskTaskIdStatusGet(@Parameter(in = ParameterIn.PATH, description = "Task Id to retrieve task execution status",required=true) @PathParam("taskId") String taskId
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.taskTaskIdStatusGet(taskId,securityContext);
    }
}
