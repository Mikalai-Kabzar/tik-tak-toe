package com.shaman.common.setup;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.testng.TestNG;

import com.shaman.common.reporter.CustomLogger;

public class TestRunner {

	private static final CustomLogger LOG = CustomLogger.getLogger(TestRunner.class);
	private static final String PARAMETER_DELIMITER = "--";
	protected TestNG testNG = new TestNG();

	private List<String> suites = new ArrayList<String>();

	@Option(name = PARAMETER_DELIMITER + "testng.xml.file", usage = "Set path to testng.xml file", required = true)
	public String pathToSuite;

	public static void main(String[] args) throws Exception {
		new TestRunner(args).run();
	}

	public TestRunner(String[] args) throws Exception {
		CmdLineParser parser = new CmdLineParser(this);
		parser.parseArgument(args);
		parser.getOptions().stream().forEach(op -> {
			String key = op.option.toString().replaceAll(PARAMETER_DELIMITER, StringUtils.EMPTY);
			String value = op.printDefaultValue();
			if (value != null) {
				System.setProperty(key, value);
			}
		});
	}

	/**
	 * Run the test suite from {@link #pathToSuite}
	 * 
	 * @throws Exception
	 */
	private void run() throws Exception {
		String workingDir = System.getProperty("user.dir");
		suites.add(workingDir + "/" + pathToSuite);

		testNG.setTestSuites(suites);

		LocalTime startTime = LocalTime.now();
		testNG.run();

		Duration duration = Duration.between(startTime, LocalTime.now());
		String durationAsString = DateTimeFormatter.ofPattern("H:mm:ss").format(LocalTime.MIDNIGHT.plus(duration));
		LOG.info("Execution time is: " + durationAsString);
	}

}
