package com.sav.fileloader.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class fulfills the condition of the task - 1.1, 1.2, 1.3.
 * 
 * Задание 1.1: Создать консольное приложение для загрузки файлов. Программа
 * должна скачивать файлы по HTTP протоколу.
 * 
 * Входные параметры: l – ссылка на файл. p – путь на файловой системе, куда
 * нужно сохранить файл. n – имя файла.
 *
 * Приветствуется, если вы будете предоставлять информацию об ошибках в удобном
 * для рядового пользователя виде, например: "нет прав на сохранение в указанной
 * директории" или "отсутствует файл по указанной ссылке". (Использовать
 * английский или русский язык)
 * 
 * Задание 1.2: Добавить возможность использовать в качестве входного параметра
 * файл со списком ссылок и названий скачиваемых файлов.
 * 
 * Задание 1.3: Добавить возможность использования отдельных потоков для
 * распараллеливания скачивания файлов по списку. Количество потоков необходимо
 * принимать в качестве еще одного входящего параметра.
 * 
 * Например: -f — путь к файлу ссылок. -p — путь на файловой системе, куда нужно
 * сохранять файлы. -t — колличство потоков (опционально).
 * 
 * Привествуются отчеты об ошибках, а так же финальный результат — сколько
 * файлов скачано, скольно не скачано и какие из них не скачаны.
 * 
 * @author AlexStrug
 *
 */
public class FirstTask extends Downloader {

	private final static Logger LOGGER = LoggerFactory.getLogger(FirstTask.class);

	public static List<Pair<String, Boolean>> listAdd = new ArrayList<Pair<String, Boolean>>();

	List<String> fileURL = new ArrayList<String>();
	List<String> fileName = new ArrayList<String>();

	/**
	 * This method fulfills the condition of the task - 1.1.
	 * 
	 * @param l
	 *            - url on file.
	 * @param p
	 *            - path on the file system where you want to save the file.
	 * @param n
	 *            - file name.
	 */
	public void startByURL(String l, String p, String n) throws Exception {

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

	/**
	 * This method fulfills the condition of the task - 1.2.
	 * 
	 * @param f
	 *            - path to the reference file.
	 * @param p
	 *            - path on the file system where you want to save the file.
	 */

	public void startByFile(String f, String p) throws Exception {

		File file = new File(f);

		Scanner readerFile = new Scanner(new FileReader(file));

		while (readerFile.hasNext()) {
			fileURL.add(readerFile.next());
			fileName.add(readerFile.next());
		}
		readerFile.close();

		for (int i = 0; i < fileURL.size(); i++) {
			try {
				download(fileURL.get(i), p, fileName.get(i));
				listAdd.add(Pair.of(fileName.get(i), true));
			} catch (FileNotFoundException e) {
				LOGGER.error("File not found: " + e.getMessage());
			} catch (IOException e) {
				LOGGER.error("This path not found or you do not have the access to save file in this place: "
						+ e.getMessage());
				listAdd.add(Pair.of(fileName.get(i), false));
			} catch (Exception e) {
				LOGGER.error("Something exception: ");
				e.printStackTrace();
				listAdd.add(Pair.of(fileName.get(i), false));
			}
		}
	}

	/**
	 * This method overloading fulfills the condition of the task - 1.3.
	 * 
	 * @param f
	 *            - path to the reference file.
	 * @param p
	 *            - path on the file system where you want to save the file.
	 * @param numberThreads
	 *            - quantity threads.
	 */
	public void startByFile(String f, String p, int numberThreads) throws Exception {

		File file = new File(f);

		Scanner readerFile = new Scanner(new FileReader(file));

		while (readerFile.hasNext()) {
			fileURL.add(readerFile.next());
			fileName.add(readerFile.next());
		}
		readerFile.close();

		// System.out.println(fileURL);
		// System.out.println(fileName);

		ExecutorService executor = Executors.newFixedThreadPool(numberThreads);

		for (int i = 0; i < fileURL.size(); i++) {
			String nameURL = fileURL.get(i);
			String nameFile = fileName.get(i);
			Runnable worker = new DownloadRunnable(nameURL, p, nameFile);
			executor.execute(worker);
		}
		executor.shutdown();

		while (!executor.isTerminated()) {

		}
		System.out.println("\nFinished all threads");
	}

	/**
	 * This method prints a report of errors and successes.
	 * 
	 * @param listAdd
	 *            - list of file names with Boolean expression on the
	 *            downloaded.
	 */
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
