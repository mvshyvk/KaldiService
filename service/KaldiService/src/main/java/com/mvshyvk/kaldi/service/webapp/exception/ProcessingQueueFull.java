package com.mvshyvk.kaldi.service.webapp.exception;

/**
 * Exception notifies that processing queue is full and new task can't be added to the queue
 */
public class ProcessingQueueFull extends Exception {

	public ProcessingQueueFull(String msg) {
		super(msg);
	}
}
