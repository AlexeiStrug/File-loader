package com.sav.fileloader.tasks;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sav.fileloader.model.Downloader;

public class FirstTask extends Downloader {

	private final static Logger LOGGER = LoggerFactory.getLogger(FirstTask.class);

	public void startFirstTast(String l, String p, String n) throws Exception {

		try {
			download(l, p, n);
		} catch (FileNotFoundException e) {
			LOGGER.error("File not found: " + e.getMessage());
		} catch (IOException e) {
			LOGGER.error(
					"This path not found or you do not have the access to save file in this place: " + e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Something exception: ");
			e.printStackTrace();
		}
	}


	
}
