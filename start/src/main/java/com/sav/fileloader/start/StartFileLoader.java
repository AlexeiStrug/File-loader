package com.sav.fileloader.start;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sav.fileloader.model.FirstTask;

public class StartFileLoader {

	private final static Logger LOGGER = LoggerFactory.getLogger(StartFileLoader.class);

	public static void main(String[] args) throws Exception {

		FirstTask start = new FirstTask();

		startCMD(start, args);

	}

	public static void startCMD(FirstTask start, String[] args) {

		String l, p, n, f, t;
		CommandLine commandLine;
		CommandLineParser parser = new DefaultParser();
		
		try {
			commandLine = parser.parse(startOptions(), args);
			if (commandLine.hasOption("l")) {
				l = commandLine.getOptionValue("l");
				p = commandLine.getOptionValue("p");
				n = commandLine.getOptionValue("n");
				start.startByURL(l, p, n);
			} else if (commandLine.hasOption("f")) {
				f = commandLine.getOptionValue("f");
				p = commandLine.getOptionValue("p");
				if (commandLine.hasOption("t")) {
					t = commandLine.getOptionValue("t");
					int numberThreads = Integer.parseInt(t);
					start.startByFile(f, p, numberThreads);
				} else {
					start.startByFile(f, p);
				}
				System.out.print("\n");
				start.printResult(FirstTask.listAdd);
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

		// String[] testArgs = { "-l",
		// "https://pp.userapi.com/c636416/v636416672/3b75b/37H7X1Rngj0.jpg",
		// "-p", "D:\\",
		// "-n", "first.jpg", };
		// String[] testArgs1 = { "-f", "C://file.txt", "-p", "D:\\", "-t", "2",
		// };

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
