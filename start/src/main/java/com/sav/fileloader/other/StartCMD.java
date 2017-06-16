package com.sav.fileloader.other;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sav.fileloader.tasks.FirstTask;
import com.sav.fileloader.tasks.SecondTask;

public class StartCMD {

	private final static Logger LOGGER = LoggerFactory.getLogger(StartCMD.class);
	
	String l, p, n, f, t;
	StartOptions startOpt = new StartOptions();
	CommandLine commandLine;

	public void startCMD(FirstTask firstTask, SecondTask secondTask, String[] args) {

		CommandLineParser parser = new DefaultParser();
		try {
			commandLine = parser.parse(startOpt.startOptions(), args);
			if (commandLine.hasOption("l")) {
				l = commandLine.getOptionValue("l");
				p = commandLine.getOptionValue("p");
				n = commandLine.getOptionValue("n");
				firstTask.startFirstTast(l, p, n);
			} else if (commandLine.hasOption("f")) {
				f = commandLine.getOptionValue("f");
				p = commandLine.getOptionValue("p");
				if (commandLine.hasOption("t")) {
					t = commandLine.getOptionValue("t");
					int numberThreads = Integer.parseInt(t);
					secondTask.startSecondTask(f, p, numberThreads);
				} else {
					secondTask.startSecondTask(f, p);
				}
				System.out.print("\n");
				secondTask.printResult(SecondTask.listAdd);
			}
		} catch (ParseException e) {
			LOGGER.error("Parse error: ");
			e.printStackTrace();
		} catch (Exception e) {
			LOGGER.error("Something exception: ");
			e.printStackTrace();
		}
	}
}
