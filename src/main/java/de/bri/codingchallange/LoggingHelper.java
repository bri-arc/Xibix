package de.bri.codingchallange;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Generell helper class for logging
 * 
 * @author benjamin
 *
 */
public class LoggingHelper {

	public static final LoggingHelper instance = new LoggingHelper();

	private LoggingHelper() {
		super();
		this.init();
	}

//	public static LoggingHelper getInstance() {
//		return LoggingHelper.instance;
//	}

	public static Logger getLogger() {
		return Logger.getGlobal(); //  Logger("");
	}

	private void init() {
		try {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream("logging.properties");
			LogManager.getLogManager().readConfiguration(is);
		} catch (SecurityException | IOException e) {
			System.err.println("WARNING: Could not open configuration file");
			System.err.println("WARNING: Logging not configured (console output only)");
		}
	}
}
