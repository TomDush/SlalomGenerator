package fr.dush.slalomgenerator.views.utils;

import java.lang.Thread.UncaughtExceptionHandler;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;

import fr.dush.slalomgenerator.events.generic.ExceptionEvent;

/**
 * Handle exceptions and redirect to logger.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@Named
public class Handler implements UncaughtExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(Handler.class);

	@Inject
	private EventBus bus;

	/** Last exception caught */
	static private Throwable lastException;

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		LOGGER.error("Catch view exception : {}", e.getMessage(), e);
		lastException = e;

		bus.post(new ExceptionEvent(this, e));
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