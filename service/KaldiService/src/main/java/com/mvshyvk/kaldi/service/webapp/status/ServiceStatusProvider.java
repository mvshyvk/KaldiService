package com.mvshyvk.kaldi.service.webapp.status;

import com.mvshyvk.kaldi.service.models.ServiceStatus;

/**
 * Interface for providing KaldiService status information
 */
public interface ServiceStatusProvider {

	/**
	 * Gets global KaldiService status
	 */
	ServiceStatus getServiceStatus();

}