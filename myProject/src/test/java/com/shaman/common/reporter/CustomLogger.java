package com.shaman.common.reporter;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class CustomLogger {
	private static final Logger LOG = Logger.getLogger(CustomLogger.class);
	private static final String CLASS_NAME = CustomLogger.class.getName();

	private static CustomLogger customLogger;

	/**
	 * Method creates CustomLogger instance. If it exists, method returns existed
	 * one
	 *
	 * @param clazz
	 *            Caller class
	 * @return CustomLogger
	 */
	public static CustomLogger getLogger(Class<?> clazz) {
		if (customLogger == null) {
			customLogger = new CustomLogger();
		}
		return customLogger;
	}

	/**
	 * Format message with dates
	 * 
	 * @param formatPattern
	 * @param args
	 * @return
	 */
	public static String formatMessage(String formatPattern, Object... args) {
		String message = String.format(formatPattern, args);
		return message;
	}

	/**
	 * Method logs at Error level
	 *
	 * @param message
	 *            Message, which will be shown in log
	 */
	public void error(String message) {
		LogAtLevel(Level.ERROR, message);
	}

	/**
	 * Method logs at Error level with String.format
	 *
	 * @param formatPattern
	 *            String format pattern
	 * @param args
	 *            format arguments
	 */
	public void error(String formatPattern, Object... args) {
		String message = formatMessage(formatPattern, args);
		error(message);
	}

	/**
	 * Method logs at Debug level
	 *
	 * @param message
	 *            Message, which will be shown in log
	 */
	public void debug(String message) {
		LogAtLevel(Level.DEBUG, message);
	}

	/**
	 * Method logs at Debug level with String.format
	 *
	 * @param formatPattern
	 *            String format pattern
	 * @param args
	 *            format arguments
	 */
	public void debug(String formatPattern, Object... args) {
		String message = formatMessage(formatPattern, args);
		debug(message);
	}

	/**
	 * Method logs at Info level
	 *
	 * @param message
	 *            Message, which will be shown in log
	 */
	public void info(String message) {
		LogAtLevel(Level.INFO, message);
	}

	/**
	 * Method logs at Info level with String.format
	 *
	 * @param formatPattern
	 *            String format pattern
	 * @param args
	 *            format arguments
	 */
	public void info(String formatPattern, Object... args) {
		String message = formatMessage(formatPattern, args);
		info(message);
	}

	/**
	 * Method logs at Trace level
	 *
	 * @param message
	 *            Message, which will be shown in log
	 */
	public void trace(String message) {
		LogAtLevel(Level.TRACE, message);
	}

	/**
	 * Method logs at Trace level with String.format
	 *
	 * @param formatPattern
	 *            String format pattern
	 * @param args
	 *            format arguments
	 */
	public void trace(String formatPattern, Object... args) {
		String message = formatMessage(formatPattern, args);
		trace(message);
	}

	/**
	 * Method logs at Warn level
	 *
	 * @param message
	 *            Message, which will be shown in log
	 */
	public void warn(String message) {
		LogAtLevel(Level.WARN, message);
	}

	/**
	 * Method logs at Warn level with String.format
	 *
	 * @param formatPattern
	 *            String format pattern
	 * @param args
	 *            format arguments
	 */
	public void warn(String formatPattern, Object... args) {
		String message = formatMessage(formatPattern, args);
		warn(message);
	}

	/**
	 * Method logs at Fatal level
	 *
	 * @param message
	 *            Message, which will be shown in log
	 */
	public void fatal(String message) {
		LogAtLevel(Level.FATAL, message);
	}

	/**
	 * Method sends a message to the log according to Level value
	 *
	 * @return Logger
	 */
	private static void LogAtLevel(Level level, Object message) {
		LOG.log(CLASS_NAME, level, message, null);
	}

}
