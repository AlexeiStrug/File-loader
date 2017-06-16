package com.sav.fileloader.tasks;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.tuple.Pair;

import com.sav.fileloader.model.Downloader;
import com.sav.fileloader.model.MyRunnable;

public class SecondTask extends Downloader {
	
	public static List<Pair<String, Boolean>> listAdd = new ArrayList<Pair<String, Boolean>>();
	
	public void startSecondTask(String f, String p, int number) throws Exception {

		File file = new File(f);

		List<String> fileURL = new ArrayList<String>();
		List<String> fileName = new ArrayList<String>();

		Scanner readerFile = new Scanner(new FileReader(file));

		while (readerFile.hasNext()) {
			fileURL.add(readerFile.next());
			fileName.add(readerFile.next());
		}
		readerFile.close();

//		System.out.println(fileURL);
//		System.out.println(fileName);

		ExecutorService executor = Executors.newFixedThreadPool(number);

		for (int i = 0; i < fileURL.size(); i++) {
			String nameURL = fileURL.get(i);
			String nameFile = fileName.get(i);
			Runnable worker = new MyRunnable(nameURL, p, nameFile);
			executor.execute(worker);
		}
		executor.shutdown();

		while (!executor.isTerminated()) {

		}
		System.out.println("\nFinished all threads");
	}

	public void printResult(List<Pair<String, Boolean>> listAdd) {
		int countFail = 0;
		int countSuccess = 0;
		for (Pair<String, Boolean> list : listAdd) {
			if (list.getRight().equals(false)) {
				System.out.println("Fail download " + list.getLeft());
				countFail++;
			} else
				countSuccess++;
		}
		System.out.println("Count failed download: " + countFail);
		System.out.println("Count success download: " + countSuccess);

	}

}
