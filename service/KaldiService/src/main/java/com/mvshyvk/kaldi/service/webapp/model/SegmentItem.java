package com.mvshyvk.kaldi.service.webapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data model for segment in recognition results file
 */
public class SegmentItem {

	@JsonProperty("AudioFile")
	private String audioFileName;
	
	@JsonProperty("Start")
	private int start;
	
	@JsonProperty("End")
	private int end;
	
	@JsonProperty("Name")
	private String channelName;

	@JsonProperty("Text")
	private String text;
	
	public String getAudioFileName() {
		return audioFileName;
	}

	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
	
	public String getName() {
		return channelName;
	}
	
	public String getText() {
		return text;
	}
}
