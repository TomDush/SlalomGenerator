package fr.dush.slalomgenerator.events.model;

public class UpdateEvent extends ModelObjectEvent {

	public <Type> UpdateEvent(Type object) {
		super(object);
	}

	@Override
	public EventType getEventType() {
		return EventType.UPDATE;
	}

}
