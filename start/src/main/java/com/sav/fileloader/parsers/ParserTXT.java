package com.sav.fileloader.parsers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sav.fileloader.model.FileLoader;

/**
 * This class implements the method startParser the interface IParser.
 * 
 * @author AlexStrug
 *
 */
public class ParserTXT implements IParser {

	private final static Logger LOGGER = LoggerFactory.getLogger(ParserTXT.class);

	/**
	 * This method takes a file that has a format "TXT" and reads its contents.
	 * 
	 * f - path to the reference file.
	 */
	@Override
	public void startParser(String f) {

		File file = new File(f);

		Scanner readerFile = null;
		try {
			readerFile = new Scanner(new FileReader(file));
		} catch (FileNotFoundException e) {
			LOGGER.error("File not found: " + e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Something exception: ");
			e.printStackTrace();
		}

		while (readerFile.hasNext()) {
			FileLoader.fileURL.add(readerFile.next());
			FileLoader.fileName.add(readerFile.next());
		}
		readerFile.close();

	}
}
