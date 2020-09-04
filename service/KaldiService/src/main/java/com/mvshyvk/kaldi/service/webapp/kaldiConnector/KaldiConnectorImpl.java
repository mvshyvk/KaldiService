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
 * speech recognition 
 */
public class KaldiConnectorImpl implements KaldiConnector {
	
	private static Logger log = Logger.getLogger(KaldiConnectorImpl.class);

	/**
	 *  Processes speech represented in TaskData
	 * @param taskData - task with information for processing
	 * @throws InterruptedException
	 */
	@Override
	public void processSpeach(TaskData taskData) throws InterruptedException {
		
		Path taskFolder = createTaskFolder(taskData);
		
		try {
			
			// Saving Wave to file 
			File waveFile = Paths.get(taskFolder.toString(), getWaveFileName(taskData)).toFile();

			try {
				Files.write(taskData.getWaveData(), waveFile);
			} 
			catch (IOException e) {

				log.error("Error happened during writing to wave file " + waveFile.getPath(), e);
				taskData.setError(e);
				return;
			}
			
			// Processing Wave file
			try {
				processWaveFile(taskFolder, waveFile, taskData);
			} 
			catch (JsonParseException | JsonMappingException e) {

				log.error("Error happend during deserializing recognition results", e);
				taskData.setError(e);
				return;
			}
			catch (IOException e) {

				log.error("Error during speech recognition ", e);
				taskData.setError(e);
				return;
			}
		}
		finally {
			// Cleanup of working temporary folder 
			try {
				FileUtils.deleteDirectory(taskFolder.toFile());
			}
			catch (IOException e) {
				log.error("Unable to remove task folder with files " + taskFolder.toString(), e);
			}
		}
	}

	/**
	 * Processes wave file performing speech recognition
	 * 
	 * @param taskFolder - working folder for temporary files and storing results
	 * @param waveFile - wave file to process
	 * @param taskData - task to store results
	 * 
	 * @throws IOException, InterruptedException, JsonParseException, JsonMappingException  
	 */
	private void processWaveFile(Path taskFolder, File waveFile, TaskData taskData)
			throws IOException, InterruptedException, JsonParseException, JsonMappingException {
		
		// Running script for speech recognition
		log.info(String.format("Processing task %s, file: %s", taskData.getTaskId(), waveFile.getPath()));
		
		Process recognitionProcess = Runtime.getRuntime().exec(new String[] {
			"python", "/speech_recognition/recognition_task.py", waveFile.getPath(), taskFolder.toString()});
		
		// Collecting script output
		String recognitionOutput = getScriptOutput(recognitionProcess);
		log.debug(recognitionOutput);

		// Waiting process termination
		int returnCode = recognitionProcess.waitFor();
		
		if (returnCode != 0) {
			
			Exception error = new Exception(String.format(
					"Recognition process returned code %d when processing %s file", returnCode, waveFile.getPath()));
			log.error(error);
			log.error("Script output: " + recognitionOutput);
			taskData.setError(error);
			return;
		}
		
		File resultsFile = Paths.get(taskFolder.toString(), "result.json").toFile();
		readResults(resultsFile, taskData);
	}

	/**
	 * Captures and returns script output
	 *  
	 * @param recognitionProcess - process to capture output
	 * @return captured output
	 * @throws IOException
	 */
	private String getScriptOutput(Process recognitionProcess) throws IOException {
		
		try (InputStream inputStream = recognitionProcess.getErrorStream()) {
			
			return new BufferedReader(new InputStreamReader(inputStream))
					  .lines()
					  .collect(Collectors.joining("\n"));
		}
	}

	/**
	 * Reads JSON file with results of speech recognition 
	 * 
	 * @param resultsFile - file to read
	 * @param taskData - task object to store results
	 * @throws JsonParseException, JsonMappingException, IOException
	 */
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

	/**
	 * Stores speech recognition results at task object
	 * 
	 * @param result - speech recognition results
	 * @param taskData - task object to store results
	 */
	private void passRecognitionResults(List<SegmentItem> result, TaskData taskData) {
		
		StringBuilder strBuilder = new StringBuilder();
		Boolean first = true;
		
		for (SegmentItem segment : result) {
			
			taskData.getTextChunks().add(new Segment()
					.segmentText(segment.getText())
					.timeStart(segment.getStart())
					.timeEnd(segment.getEnd()));
			
			if (!first) {
				strBuilder.append("; ");
			}
			first = false;
			
			strBuilder.append(segment.getText());
		}
		
		taskData.setText(strBuilder.toString());
	}

	/**
	 * Creates temporary folder for performing task
	 * 
	 * @param taskData -  task to perform
	 * @return path to created folder
	 */
	private Path createTaskFolder(TaskData taskData) {
		
		Path folder = Paths.get(getTempFolder(), taskData.getTaskId());
		folder.toFile().mkdirs();
		return folder;
	}

	/**
	 * Returns path to temporary folder
	 */
	private String getTempFolder() {
		return System.getProperty("catalina.base") + "/temp";
	}

	/**
	 * Generates name of Wave file
	 */
	private String getWaveFileName(TaskData taskData) {
		return taskData.getTaskId().substring(0, 5) + ".wav";
	}

}
