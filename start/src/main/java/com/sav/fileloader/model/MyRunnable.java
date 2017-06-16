package com.sav.fileloader.model;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sav.fileloader.tasks.SecondTask;

public class MyRunnable implements Runnable {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(MyRunnable.class);

	private String l1;
	private String l2;
	private String p;

	public MyRunnable(String l1, String p, String l2) {
		this.l1 = l1;
		this.l2 = l2;
		this.p = p;
	}

	public void run() {
		try {
			SecondTask secondTask = new SecondTask();
			secondTask.download(l1, p, l2);
			SecondTask.listAdd.add(Pair.of(l2, true));
		} catch (FileNotFoundException e) {
			LOGGER.error("File not found: " + e.getMessage());
		} catch (IOException e) {
			LOGGER.error(
					"This path not found or you do not have the access to save file in this place: " + e.getMessage());
			SecondTask.listAdd.add(Pair.of(l2, false));
		} catch (Exception e) {
			LOGGER.error("Something exception: ");
			e.printStackTrace();
			SecondTask.listAdd.add(Pair.of(l2, false));
		}
	}
}
