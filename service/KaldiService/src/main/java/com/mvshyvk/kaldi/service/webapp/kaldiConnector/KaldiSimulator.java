package com.mvshyvk.kaldi.service.webapp.kaldiConnector;

import com.mvshyvk.kaldi.service.webapp.task.TaskData;

public class KaldiSimulator implements KaldiConnector {

	@Override
	public void processSpeach(TaskData taskData) throws InterruptedException {

		Thread.sleep(10000);
	}

}
