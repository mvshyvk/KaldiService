package com.mvshyvk.kaldi.service.api.impl;

import com.mvshyvk.kaldi.service.api.*;
import com.mvshyvk.kaldi.service.api.NotFoundException;
import com.mvshyvk.kaldi.service.models.Result;
import com.mvshyvk.kaldi.service.models.Result.StatusEnum;
import com.mvshyvk.kaldi.service.models.TaskId;
import com.mvshyvk.kaldi.service.webapp.KaldiServiceAppContext;
import com.mvshyvk.kaldi.service.webapp.exception.ProcessingQueueFull;
import com.mvshyvk.kaldi.service.webapp.model.TaskData;
import com.mvshyvk.kaldi.service.webapp.model.TaskStatus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.apache.log4j.Logger;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2020-04-23T21:31:33.644Z[GMT]")
public class TaskApiServiceImpl extends TaskApiService {
	
	private static Logger log = Logger.getLogger(TaskApiServiceImpl.class);
	
	@Override
	public Response taskPost(InputStream inputStream, SecurityContext securityContext) throws NotFoundException {
		
		if (inputStream != null) {
			
			TaskId taskId = null;
			try {
				byte[] data = readData(inputStream);
				taskId = KaldiServiceAppContext.taskHandlerService.postTask(data);
			} catch (ProcessingQueueFull e) {
				
				log.debug("Processing queue is full");
				return Response.status(Status.TOO_MANY_REQUESTS).build();
			}
			catch (IOException e) {

				log.error("IO exception has happened", e);
				return Response.status(Status.BAD_REQUEST).build();
			}
			
			return Response.accepted().entity(taskId).build();
		}
		
		// TODO: Add BAD_REQUEST (400) to YAML and description
		log.error("InputStream was not specified. Unable to read data");
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
		
		TaskStatus status = KaldiServiceAppContext.taskHandlerService.getTaskStatus(new TaskId().taskId(taskId));
		log.debug(String.format("Task %s status: %s", taskId, status));
		
		if (status == TaskStatus.enInQueue || status == TaskStatus.enInProgress) {

			return Response.noContent().build();
		}
		else if (status == TaskStatus.enCompleted) {
			
			TaskData taskData = KaldiServiceAppContext.taskHandlerService.getTaskData(new TaskId().taskId(taskId));
			
			Result result = new Result();
			result.setStatus(StatusEnum.DONE);
			result.setTaskId(taskId);
			result.setText(taskData.getText());
			result.setTextChunks(taskData.getTextChunks());
			
			log.debug(String.format("Task %s results: %s", taskId, result));
			return Response.ok().entity(result).build();
		}
		
		return Response.status(Status.NOT_FOUND).build();
	}
}
