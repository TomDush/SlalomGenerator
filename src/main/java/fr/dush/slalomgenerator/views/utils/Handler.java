package fr.dush.slalomgenerator.views.utils;

import java.lang.Thread.UncaughtExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handle exceptions and redirect to logger.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
public class Handler implements UncaughtExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

	/** Last exception caught */
	static private Throwable lastException;

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		LOGGER.error("Catch view exception : {}", e.getMessage(), e);
		lastException = e;

	}

	/**
	 * Return last exception (may be null), and reset.
	 *
	 * @return Last throws exception, may be null.
	 */
	public static Throwable getLastException() {
		try {
			return lastException;
		} finally {
			// Reset exception
			lastException = null;
		}
	}

	/**
	 * Last treatment finish with error
	 *
	 * @return
	 */
	public static boolean hasError() {
		return null != lastException;
	}

}