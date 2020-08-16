package com.mvshyvk.kaldi.service.webapp.model;

import java.nio.ByteBuffer;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import com.mvshyvk.kaldi.service.models.Segment;

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
		
		taskId = generateTaskId();
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

	// TODO: Extract as a utility class 
	private static String generateTaskId() {
		
		UUID uuid = UUID.randomUUID();
		String taskId = uuidToBase64(uuid).substring(0, 22);
		return makeURLSafe(taskId);		
	}
	
	private static String makeURLSafe(String b64Id) {		
		return b64Id
				.replace('=', 'e')
				.replace('+', 'p')
				.replace('/', 's');		
	}

	private static String uuidToBase64(UUID uuid) {
		
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		return Base64.getEncoder().encodeToString(bb.array());
	}

}
