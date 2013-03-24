package fr.dush.slalomgenerator.events.model;

public class CreateRequestEvent extends ModelEvent {

	public CreateRequestEvent(Class<?> clazz) {
		super(clazz);
	}

	@Override
	public EventType getEventType() {
		return EventType.CREATE_REQUEST;
	}

}
