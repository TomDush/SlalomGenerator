package fr.dush.slalomgenerator.events.model;

public class EditEvent extends ModelObjectEvent {

	public <Type> EditEvent(Type object) {
		super(object);
	}

	@Override
	public EventType getEventType() {
		return EventType.UPDATE;
	}

}
