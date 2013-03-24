package fr.dush.slalomgenerator.business.event;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;

/**
 * Provide to spring's context EventBus (from <i>Guava</i>).
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 */
@Component
public class EventBusProvider {

	@Bean
	public EventBus provideEventBus() {
		return new EventBus();
	}
}
