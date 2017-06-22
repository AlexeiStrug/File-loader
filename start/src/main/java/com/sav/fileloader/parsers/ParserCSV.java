package com.sav.fileloader.parsers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sav.fileloader.model.FileLoader;

/**
 * This class implements the method startParser the interface IParser.
 * 
 * @author AlexStrug
 *
 */
public class ParserCSV implements IParser {

	private final static Logger LOGGER = LoggerFactory.getLogger(ParserCSV.class);

	/**
	 * This method takes a file that has a format "CSV" and reads its contents.
	 * 
	 * f - path to the reference file.
	 */
	@Override
	public void startParser(String f) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			LOGGER.error("File not found: " + e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Something exception: ");
			e.printStackTrace();
		}

		String sCurrentLine;
		try {
			while ((sCurrentLine = br.readLine()) != null) {

				String[] information = sCurrentLine.split(";");
				FileLoader.fileURL.add(information[0]);
				FileLoader.fileName.add(information[1]);
			}
		} catch (Exception e) {
			LOGGER.error("Something exception: ");
			e.printStackTrace();
		}

	}
}
