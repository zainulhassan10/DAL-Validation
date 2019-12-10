package com.quarrio.dal.validations;

import org.apache.commons.cli.*;

/**
 * @author Zain ul Hassan
 *
 */
public class CommandLineArgument {

	private static final String SERVER_NAME = "servername";
	private static final String FILE_INPUT = "fileinput"; // Read Path
	private static final String FILE_OUTPUT = "fileoutput"; // Write Path

	@SuppressWarnings("deprecation")
	public static CommandLine getArrguments(String[] args) {// Define Options
		Options options = new Options();
		options.addOption(SERVER_NAME, true, "Server Name");// Description Only
		options.addOption(FILE_INPUT, true, "File Input Path");
		options.addOption(FILE_OUTPUT, true, "File Output Path");

		CommandLineParser parser = new BasicParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		return cmd;
	}
}