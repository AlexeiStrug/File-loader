package com.sav.fileloader.tasks;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sav.fileloader.model.Downloader;


/**
 * This class fulfills the condition of the task - 1.1.
 * 
 * Задание 1.1: 
 * Создать консольное приложение для загрузки файлов.
 * Программа должна скачивать файлы по HTTP протоколу.
 * 
 * Входные параметры:
 * l – ссылка на файл. 
 * p – путь на файловой системе, куда нужно сохранить файл. 
 * n – имя файла. 
 *
 * Приветствуется, если вы будете предоставлять информацию об ошибках в удобном
 * для рядового пользователя виде, например: "нет прав на сохранение в указанной
 * директории" или "отсутствует файл по указанной ссылке". (Использовать
 * английский или русский язык)
 * 
 * @author AlexStrug
 *
 */
public class FirstTask extends Downloader {

	private final static Logger LOGGER = LoggerFactory.getLogger(FirstTask.class);

	/**
	 * This method fulfills the condition of the task - 1.1.
	 * 
	 * @param l - url on file.
	 * @param p - path on the file system where you want to save the file.
	 * @param n - file name.
	 */
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
