package fr.dush.slalomgenerator.events.model.dialog;

import fr.dush.slalomgenerator.events.model.EventType;
import fr.dush.slalomgenerator.events.model.ModelObjectEvent;

public class EditRequestEvent extends ModelObjectEvent {

	public <Type> EditRequestEvent(Type object) {
		super(object);
	}

	@Override
	public EventType getEventType() {
		return EventType.UPDATE;
	}

}
