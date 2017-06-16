package com.sav.fileloader.start;

import com.sav.fileloader.other.StartCMD;
import com.sav.fileloader.tasks.FirstTask;
import com.sav.fileloader.tasks.SecondTask;

public class StartFileLoader {

	public static void main(String[] args) throws Exception {
	
		FirstTask firstTask = new FirstTask();
		SecondTask secondTask = new SecondTask();
		
		StartCMD start = new StartCMD();
		start.startCMD(firstTask, secondTask, args);

	}
}
