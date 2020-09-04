package com.mvshyvk.kaldi.service.webapp.kaldiConnector;

import com.mvshyvk.kaldi.service.webapp.model.TaskData;

/**
 * Interface for inter-process communication with Python scripts that do data processing 
 */
public interface KaldiConnector {
	
	/**
	 *  Processes speech represented in TaskData
	 * @param taskData - task with information for processing
	 * @throws InterruptedException
	 */
	void processSpeach(TaskData taskData) throws InterruptedException;
}
