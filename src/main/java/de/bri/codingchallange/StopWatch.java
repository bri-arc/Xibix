package de.bri.codingchallange;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.NoArgsConstructor;

/**
 * Simple class with the functionality of a stopwatch
 * 
 * @author benjamin
 *
 */
@NoArgsConstructor
public class StopWatch extends AbstractBase {
	
	private Long startTime;
	
	private Long elapsedTime;
	
	/**
	 * Function to start time measuring 
	 */
	public void start() {
		this.startTime = System.nanoTime();
	}
	
	/**
	 * Function to stop time measuring 
	 */
	public void stop() {
		this.elapsedTime = System.nanoTime() - this.startTime;
	}

	/**
	 * Prints the execution times to Logger
	 */
	public void printExecutionTime() {
		this.getLogger().info("Total execution time in millis: " + this.elapsedTime / 1000000);
        var value = new BigDecimal(this.elapsedTime);
        value = value.divide(new BigDecimal(1000000000), 2, RoundingMode.UP);
        this.getLogger().info("Total execution time in seconds: " + value.doubleValue());
	}
}