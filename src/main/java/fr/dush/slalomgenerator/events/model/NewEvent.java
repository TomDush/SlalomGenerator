package fr.dush.slalomgenerator.events.model;

public class NewEvent extends ModelObjectEvent {

	public <Type> NewEvent(Type object) {
		super(object);
	}

	@Override
	public EventType getEventType() {
		return EventType.NEW;
	}

}
