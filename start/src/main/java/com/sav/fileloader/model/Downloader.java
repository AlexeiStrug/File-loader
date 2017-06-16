package com.sav.fileloader.model;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * This class implements the method download the interface IDownloader
 * 
 * @author AlexStrug
 *
 */
public abstract class Downloader implements IDownloader {

	public Path download(String sourceURL, String targetDirectory, String fileName) throws IOException {
		if (sourceURL != null || targetDirectory != null || fileName != null) {
			URL url = new URL(sourceURL);
			Path targetPath = new File(targetDirectory + File.separator + fileName).toPath();
			Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
			return targetPath;
		} else
			return null;
	}

}
