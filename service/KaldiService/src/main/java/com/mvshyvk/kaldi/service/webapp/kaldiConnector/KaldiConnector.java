package com.mvshyvk.kaldi.service.webapp.kaldiConnector;

import com.mvshyvk.kaldi.service.webapp.model.TaskData;

/**
 * Interface for inter-process communication with Python scripts that do data processing 
 */
public interface KaldiConnector {
	
	void processSpeach(TaskData taskData) throws InterruptedException;
}
