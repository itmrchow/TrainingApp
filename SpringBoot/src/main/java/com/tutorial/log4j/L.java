package com.tutorial.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class L {
	private static final Logger LOGGER = LogManager.getLogger();

	private L() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public static void t(String msg) {
		LOGGER.trace(msg);
	}

	public static void d(String msg) {
		LOGGER.debug(msg);
	}

	public static void i(String msg) {
		LOGGER.info(msg);
	}

	public static void w(String msg) {
		LOGGER.warn(msg);
	}

	public static void e(String msg) {
		LOGGER.error(msg);
	}

}
