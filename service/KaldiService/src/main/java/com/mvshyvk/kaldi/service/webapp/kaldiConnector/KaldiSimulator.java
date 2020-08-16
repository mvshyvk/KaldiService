package com.mvshyvk.kaldi.service.webapp.kaldiConnector;


import org.apache.log4j.Logger;
import com.mvshyvk.kaldi.service.webapp.task.TaskData;

/**
 * Simulator of task execution
 */
public class KaldiSimulator implements KaldiConnector {
	
	private static Logger log = Logger.getLogger(KaldiSimulator.class);

	@Override
	public void processSpeach(TaskData taskData) throws InterruptedException {

		log.debug("Simulating execution of task " + taskData.getTaskId());
		Thread.sleep(10000);
		log.debug("Finished simulating execution of task " + taskData.getTaskId());
	}

}
