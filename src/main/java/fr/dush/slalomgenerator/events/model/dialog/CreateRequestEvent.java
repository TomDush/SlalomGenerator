package fr.dush.slalomgenerator.events.model.dialog;

import fr.dush.slalomgenerator.events.model.EventType;
import fr.dush.slalomgenerator.events.model.ModelEvent;

public class CreateRequestEvent extends ModelEvent {

	public CreateRequestEvent(Class<?> clazz) {
		super(clazz);
	}

	@Override
	public EventType getEventType() {
		return EventType.CREATE_REQUEST;
	}

}
