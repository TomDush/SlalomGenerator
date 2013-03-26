package fr.dush.slalomgenerator.business.event;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

/**
 * Log dead events : event without listener...
 *
 * @author Thomas Duchatelle
 */
@Named
public class EventLogger {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventLogger.class);

	@Subscribe
	public void logEvent(DeadEvent event) {
		LOGGER.info("Received but not treated : {}", event.getEvent());
	}
}
