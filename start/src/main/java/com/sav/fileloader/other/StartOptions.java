package com.sav.fileloader.other;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class StartOptions {

	public Options startOptions() {

//		String[] testArgs = { "-l", "https://pp.userapi.com/c636416/v636416672/3b75b/37H7X1Rngj0.jpg", "-p", "D:\\",
//				"-n", "first.jpg", };
//		String[] testArgs1 = { "-f", "C://file.txt", "-p", "D:\\", "-t", "2", };

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
