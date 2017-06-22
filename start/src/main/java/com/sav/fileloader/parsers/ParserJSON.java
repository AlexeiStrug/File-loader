package com.sav.fileloader.parsers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sav.fileloader.model.FileLoader;

/**
 * This class implements the method startParser the interface IParser.
 * 
 * @author AlexStrug
 *
 */
public class ParserJSON implements IParser {

	private final static Logger LOGGER = LoggerFactory.getLogger(ParserJSON.class);

	/**
	 * This method takes a file that has a format "JSON" and reads its contents.
	 * 
	 * f - path to the reference file.
	 */
	@Override
	public void startParser(String f) {

		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(f));

			JSONObject jsonObject = (JSONObject) obj;

			JSONArray solutions = (JSONArray) jsonObject.get("download");
			Iterator iterator = solutions.iterator();
			while (iterator.hasNext()) {
				JSONObject slide = (JSONObject) iterator.next();
				FileLoader.fileURL.add((String) slide.get("url"));
				FileLoader.fileName.add((String) slide.get("fileName"));
			}
		} catch (FileNotFoundException e) {
			LOGGER.error("File not found: " + e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Something exception: ");
			e.printStackTrace();
		}
	}
}
