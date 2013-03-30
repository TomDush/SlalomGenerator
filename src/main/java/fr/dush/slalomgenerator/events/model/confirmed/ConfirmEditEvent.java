package fr.dush.slalomgenerator.events.model.confirmed;

import fr.dush.slalomgenerator.events.model.EventType;
import fr.dush.slalomgenerator.events.model.ModelObjectEvent;

public class ConfirmEditEvent extends ModelObjectEvent {

	public <Type> ConfirmEditEvent(Object source, Type object) {
		super(object);
		setSource(source);
	}

	@Override
	public EventType getEventType() {
		return EventType.UPDATE;
	}

}
