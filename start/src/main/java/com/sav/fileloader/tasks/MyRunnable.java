package com.sav.fileloader.tasks;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This method parallelizes and executes the method download on a number of
 * threads
 * 
 * @author AlexStrug
 *
 */
public class MyRunnable implements Runnable {

	private final static Logger LOGGER = LoggerFactory.getLogger(MyRunnable.class);

	private String nameURL;
	private String nameFile;
	private String targetDirectory;

	public MyRunnable(String nameURL, String targetDirectory, String nameFile) {
		this.nameURL = nameURL;
		this.nameFile = nameFile;
		this.targetDirectory = targetDirectory;
	}

	public void run() {

		SecondTask secondTask = new SecondTask();
		try {
			secondTask.download(nameURL, targetDirectory, nameFile);
			SecondTask.listAdd.add(Pair.of(nameFile, true));
		} catch (FileNotFoundException e) {
			LOGGER.error("File not found: " + e.getMessage());
		} catch (IOException e) {
			LOGGER.error(
					"This path not found or you do not have the access to save file in this place: " + e.getMessage());
			SecondTask.listAdd.add(Pair.of(nameFile, false));
		} catch (Exception e) {
			LOGGER.error("Something exception: ");
			e.printStackTrace();
			SecondTask.listAdd.add(Pair.of(nameFile, false));
		}
	}
}
