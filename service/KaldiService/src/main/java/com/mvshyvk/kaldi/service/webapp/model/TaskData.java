package com.mvshyvk.kaldi.service.webapp.model;

import java.util.ArrayList;
import java.util.List;
import com.mvshyvk.kaldi.service.models.Segment;
import com.mvshyvk.kaldi.service.webapp.utils.TaskIdGenerator;

/**
 * Data model with data to process and results of processing 
 */
public class TaskData {
	
	private String taskId;
	private byte[] waveData;
	
	private String recognizedText;
	private List<Segment> recognizedTextChunks = new ArrayList<Segment>();
	private Exception error;
	
	/**
	 * Constructor
	 * 
	 * @param data - data for processing 
	 */
	public TaskData(byte[] data) {
		
		taskId = TaskIdGenerator.generateTaskId();
		waveData = data;
	}
	
	public String getTaskId() {
		return taskId;
	}
	
	public byte[] getWaveData() {
		return waveData;
	}

	public String getText() {
		return recognizedText;
	}
	
	public void setText(String text) {
		recognizedText = text;
	}

	public List<Segment> getTextChunks() {
		return recognizedTextChunks;
	}

	public void minimizeMemoryAllocation() {
		waveData = null;
	}
	
	public Exception getError() {
		return error;
	}
	
	public void setError(Exception error) {
		this.error = error;
	}

}
