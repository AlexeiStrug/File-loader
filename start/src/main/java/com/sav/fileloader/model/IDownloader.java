package com.sav.fileloader.model;

import java.io.IOException;
import java.nio.file.Path;

public interface IDownloader {
	
	Path download(String sourceURL, String targetDirectory, String fileName) throws IOException;
	
}
