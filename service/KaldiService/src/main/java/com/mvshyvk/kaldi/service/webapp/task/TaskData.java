package com.mvshyvk.kaldi.service.webapp.task;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

public class TaskData {
	
	private String taskId;
	private byte[] waveData;
	
	public TaskData(byte[] data) {
		
		taskId = generateTaskId();
		waveData = data;
	}
	
	public String getTaskId() {
		return taskId;
	}

	static private String generateTaskId() {
		
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
