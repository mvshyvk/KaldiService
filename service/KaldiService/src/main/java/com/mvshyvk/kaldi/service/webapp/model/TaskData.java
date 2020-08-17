package com.mvshyvk.kaldi.service.webapp.model;

import java.time.OffsetDateTime;
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
	private List<Segment> recognizedTextChunks;
	
	/**
	 * Constructor
	 * 
	 * @param data - data for processing 
	 */
	public TaskData(byte[] data) {
		
		taskId = TaskIdGenerator.generateTaskId();
		waveData = data;
		
		// TODO: Hard-coded values just for testing
		generateFakeResults();
	}

	private void generateFakeResults() {
		
		recognizedText = "Some text";
		recognizedTextChunks = new ArrayList<Segment>();
		recognizedTextChunks.add(new Segment().segmentText("Some").timeStart(OffsetDateTime.now()).timeEnd(OffsetDateTime.now().plusSeconds(1)));
		recognizedTextChunks.add(new Segment().segmentText("text").timeStart(OffsetDateTime.now().plusSeconds(1)).timeEnd(OffsetDateTime.now().plusSeconds(2)));
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

	public List<Segment> getTextChunks() {
		return recognizedTextChunks;
	}

	public void minimizeMemoryAllocation() {
		waveData = null;
	}

}
