package fr.dush.slalomgenerator.business.event;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import fr.dush.slalomgenerator.events.UIEvent;

@Named
public class EventBusLogger {

	private static final Logger LOGGER = LoggerFactory.getLogger(EventBusLogger.class);

	@Inject
	public EventBusLogger(EventBus bus) {
		bus.register(this);
	}

	@Subscribe
	public void logEvent(UIEvent event) {
		LOGGER.info("Received {}", event);
	}
}
