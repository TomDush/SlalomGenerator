package fr.dush.slalomgenerator.events.model.confirmed;

import fr.dush.slalomgenerator.events.model.EventType;
import fr.dush.slalomgenerator.events.model.ModelObjectEvent;

public class ConfirmDeleteEvent extends ModelObjectEvent {

	public ConfirmDeleteEvent(Object source, Object object) {
		super(object);
		setSource(source);
	}

	@Override
	public EventType getEventType() {
		return EventType.DELETE;
	}

}
