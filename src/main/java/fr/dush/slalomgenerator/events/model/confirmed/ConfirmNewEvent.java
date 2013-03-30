package fr.dush.slalomgenerator.events.model.confirmed;

import fr.dush.slalomgenerator.events.model.EventType;
import fr.dush.slalomgenerator.events.model.ModelObjectEvent;

public class ConfirmNewEvent extends ModelObjectEvent {

	public <Type> ConfirmNewEvent(Object source, Type object) {
		super(object);
		setSource(source);
	}

	@Override
	public EventType getEventType() {
		return EventType.NEW;
	}

}
