package fr.dush.slalomgenerator.events.model.dialog;

import fr.dush.slalomgenerator.events.model.EventType;
import fr.dush.slalomgenerator.events.model.ModelObjectEvent;

public class DeleteRequestEvent extends ModelObjectEvent {

	public DeleteRequestEvent(Object object) {
		super(object);
	}

	@Override
	public EventType getEventType() {
		return EventType.DELETE;
	}

}
