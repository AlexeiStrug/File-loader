package com.sav.fileloader.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements the method download the interface IDownloader
 * 
 * @author AlexStrug
 *
 */
public abstract class Downloader implements IDownloader {

	private final static Logger LOGGER = LoggerFactory.getLogger(Downloader.class);
	
	public Path download(String sourceURL, String targetDirectory, String fileName) throws IOException {
		if (sourceURL != null || targetDirectory != null || fileName != null) {
			URL url = new URL(sourceURL);
			Path targetPath = new File(targetDirectory + File.separator + fileName).toPath();
			Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
			return targetPath;
		} else
			return null;
	}

	/**
	 * This method parallelizes and executes the method download on a number of
	 * threads
	 * 
	 * @author AlexStrug
	 *
	 */
	public class DownloadRunnable implements Runnable {

		private String nameURL;
		private String nameFile;
		private String targetDirectory;

		public DownloadRunnable(String nameURL, String targetDirectory, String nameFile) {
			this.nameURL = nameURL;
			this.nameFile = nameFile;
			this.targetDirectory = targetDirectory;
		}

		public void run() {

			FirstTask start = new FirstTask();
			try {
				start.download(nameURL, targetDirectory, nameFile);
				FirstTask.listAdd.add(Pair.of(nameFile, true));
			} catch (FileNotFoundException e) {
				LOGGER.error("File not found: " + e.getMessage());
			} catch (IOException e) {
				LOGGER.error("This path not found or you do not have the access to save file in this place: "
						+ e.getMessage());
				FirstTask.listAdd.add(Pair.of(nameFile, false));
			} catch (Exception e) {
				LOGGER.error("Something exception: ");
				e.printStackTrace();
				FirstTask.listAdd.add(Pair.of(nameFile, false));
			}
		}
	}
}
