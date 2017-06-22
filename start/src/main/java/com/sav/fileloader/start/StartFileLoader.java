package com.sav.fileloader.start;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sav.fileloader.model.FileLoader;

public class StartFileLoader {

	private final static Logger LOGGER = LoggerFactory.getLogger(StartFileLoader.class);

	public static String ERROR_MESSAGE = "Error, required input parameters:\n" + "\n"
			+ "  -l – url on file OR -f – path to the reference file with url\n"
			+ "  -p – path on the file system where you want to save the file\n"
			+ "  -n – file name(with argument -l)\n" + "  -t — quantity threads (optional)\n";

	public static void main(String[] args) {

		FileLoader startFileLoader = new FileLoader();

		startCMD(startFileLoader, args);

	}

	public static void startCMD(FileLoader startFileLoader, String[] args) {

		String l, p, n, f, t;
		CommandLine commandLine;
		CommandLineParser parser = new DefaultParser();

		if (args.length < 2) {
			System.out.println(ERROR_MESSAGE);
			return;
		}

		try {
			commandLine = parser.parse(startOptions(), args);
			if (commandLine.hasOption("l")) {
				l = commandLine.getOptionValue("l");
				p = commandLine.getOptionValue("p");
				n = commandLine.getOptionValue("n");
				startFileLoader.startByURL(l, p, n);
			} else if (commandLine.hasOption("f")) {
				f = commandLine.getOptionValue("f");
				p = commandLine.getOptionValue("p");
				if (commandLine.hasOption("t")) {
					t = commandLine.getOptionValue("t");
					int numberThreads = Integer.parseInt(t);
					startFileLoader.startByFile(f, p, numberThreads);
				} else {
					startFileLoader.startByFile(f, p);
				}
				System.out.print("\n");
				startFileLoader.printResult(FileLoader.listAdd);
			}
		} catch (ParseException e) {
			LOGGER.error("Parse error: ");
			e.printStackTrace();
		} catch (Exception e) {
			LOGGER.error("Something exception: ");
			e.printStackTrace();
		}
	}

	public static Options startOptions() {

		Option option_l = Option.builder("l").hasArg().desc("The l option - url on file").build();
		Option option_p = Option.builder("p").hasArg().required(true)
				.desc("The p option - path on the save file on the file system").build();
		Option option_n = Option.builder("n").hasArg().desc("The n option - file name").build();
		Option option_f = Option.builder("f").hasArg().desc("The f option - path on the file system to a text file")
				.build();
		Option option_t = Option.builder("t").hasArg().desc("The t option - number of threads").build();

		Options options = new Options();
		options.addOption(option_l);
		options.addOption(option_p);
		options.addOption(option_n);
		options.addOption(option_f);
		options.addOption(option_t);

		return options;
	}
}
