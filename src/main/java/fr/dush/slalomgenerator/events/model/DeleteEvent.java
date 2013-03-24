package fr.dush.slalomgenerator.events.model;

public class DeleteEvent extends ModelObjectEvent {

	public DeleteEvent(Object object) {
		super(object);
	}

	@Override
	public EventType getEventType() {
		return EventType.DELETE;
	}

}
