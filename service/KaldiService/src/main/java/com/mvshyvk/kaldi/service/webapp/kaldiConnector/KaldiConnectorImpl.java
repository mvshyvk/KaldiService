package com.mvshyvk.kaldi.service.webapp.kaldiConnector;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.mvshyvk.kaldi.service.models.Segment;
import com.mvshyvk.kaldi.service.webapp.model.SegmentItem;
import com.mvshyvk.kaldi.service.webapp.model.TaskData;


/**
 * Class that implements inter-process communication with Python scripts which perform 
 * speach recognition 
 */
public class KaldiConnectorImpl implements KaldiConnector {
	
	private static Logger log = Logger.getLogger(KaldiConnectorImpl.class);

	@Override
	public void processSpeach(TaskData taskData) throws InterruptedException {
		
		// TODO: read and store log/diagnostic data
		Path taskFolder = createTaskFolder(taskData);
		
		try {
			
			File waveFile = Paths.get(taskFolder.toString(), getWaveFileName(taskData)).toFile();
			
			try {
				Files.write(taskData.getWaveData(), waveFile);
			} 
			catch (IOException e) {
				
				log.error("Error happened during writing to wave file " + waveFile.getPath(), e);
				setTaskErrorStatus(taskData, e);
				return;
			}
			
			try {
				
				Process recognitionProcess = Runtime.getRuntime().exec(new String[] {
					"python", "/speech_recognition/recognition_task.py", waveFile.getPath(), taskFolder.toString()});
				
				try (InputStream inputStream = recognitionProcess.getErrorStream()) {
					
					String processOutput = new BufferedReader(new InputStreamReader(inputStream))
							  .lines().collect(Collectors.joining("\n"));
					log.debug(processOutput);
				}

				int returnCode = recognitionProcess.waitFor();
				
				if (returnCode != 0) {
					log.info(String.format(
							"Recognition process returned code %d when processing %s file", returnCode, waveFile.getPath()));
					return;
				}
				
				File resultsFile = Paths.get(taskFolder.toString(), "result.json").toFile();
				readResults(resultsFile, taskData);
			} 
			catch (JsonParseException | JsonMappingException e) {
				
				log.error("Error happend during deserializing recognition results", e);
				setTaskErrorStatus(taskData, e);
				return;
			}
			catch (IOException e) {

				log.error("Error during speech recognition ", e);
				setTaskErrorStatus(taskData, e);
				return;
			}
		}
		finally {
			try {

				FileUtils.deleteDirectory(taskFolder.toFile());
			}
			catch (IOException e) {
				log.error("Unable to remove task folder with files " + taskFolder.toString(), e);
			}
		}
	}

	private void readResults(File resultsFile, TaskData taskData) throws JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			List<SegmentItem> result = Arrays.asList(mapper.readValue(resultsFile, SegmentItem[].class));
			passRecognitionResults(result, taskData);
		}
		catch (IOException e) {
			
			log.error("Unable to read recognition results from " + resultsFile.getPath(), e);
			throw e;
		}
	}

	private void passRecognitionResults(List<SegmentItem> result, TaskData taskData) {
		
		StringBuilder strBuilder = new StringBuilder(); 
		for (SegmentItem segment : result) {
			
			taskData.getTextChunks().add(new Segment()
					.segmentText(segment.getText())
					.timeStart(segment.getStart())
					.timeEnd(segment.getEnd()));
			
			strBuilder.append(segment.getText());
			strBuilder.append("\n");
		}
		
		taskData.setText(strBuilder.toString());
	}

	private void setTaskErrorStatus(TaskData taskData, IOException e) {
		// TODO: Needs to be implemented
	}

	private Path createTaskFolder(TaskData taskData) {
		
		Path folder = Paths.get(getTempFolder(), taskData.getTaskId());
		folder.toFile().mkdirs();
		return folder;
	}

	private String getTempFolder() {
		return System.getProperty("catalina.base") + "/temp";
	}
	
	private String getWaveFileName(TaskData taskData) {
		return taskData.getTaskId().substring(0, 5) + ".wav";
	}

}
