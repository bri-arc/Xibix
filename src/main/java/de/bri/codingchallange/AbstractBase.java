package de.bri.codingchallange;

import java.util.logging.Logger;

/**
 * Simple abstract base class for generell logic
 * 
 * @author benjamin
 *
 */
public abstract class AbstractBase {
	
	/**
	 * static instance for logging
	 */
	private static Logger logger = LoggingHelper.getLogger();
	
	public Logger getLogger() {
		return AbstractBase.logger;
	}
}
