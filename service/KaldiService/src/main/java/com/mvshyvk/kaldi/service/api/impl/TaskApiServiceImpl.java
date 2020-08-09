package com.mvshyvk.kaldi.service.api.impl;

import com.mvshyvk.kaldi.service.api.*;
import com.mvshyvk.kaldi.service.api.NotFoundException;
import com.mvshyvk.kaldi.service.models.TaskId;
import com.mvshyvk.kaldi.service.webapp.KaldiServiceAppContext;
import com.mvshyvk.kaldi.service.webapp.exception.ProcessingQueueFull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2020-04-23T21:31:33.644Z[GMT]")
public class TaskApiServiceImpl extends TaskApiService {
	
	@Override
	public Response taskPost(InputStream inputStream, SecurityContext securityContext) throws NotFoundException {
		
		if (inputStream != null) {
			
			TaskId taskId = null;
			try {
				byte[] data = readData(inputStream);
				taskId = KaldiServiceAppContext.taskHandler.postTask(data);
			} catch (ProcessingQueueFull e) {
				
				return Response.status(Status.TOO_MANY_REQUESTS).build();
			}
			catch (IOException e) {
				
				return Response.status(Status.BAD_REQUEST).build();
			}
			
			return Response.accepted().entity(taskId).build();
		}
		// TODO: Add BAD_REQUEST (400) to YAML and description
		return Response.status(Status.BAD_REQUEST).build();
	}

	/**
	 * Reads data from input stream to byte array
	 * 
	 * @throws IOException
	 */
	private byte[] readData(InputStream inputStream) throws IOException {
		
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		
		byte[] bufferChunk = new byte[1024];
		int readBytes;
		
		while((readBytes = inputStream.read(bufferChunk)) > 0) {
			byteStream.write(bufferChunk, 0, readBytes);
		}
		
		return byteStream.toByteArray();
	}

	@Override
	public Response taskTaskIdStatusGet(String taskId, SecurityContext securityContext) throws NotFoundException {
		// do some magic!
		return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
	}
}
