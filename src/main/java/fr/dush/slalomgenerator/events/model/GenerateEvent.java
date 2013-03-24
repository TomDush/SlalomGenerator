package fr.dush.slalomgenerator.events.model;

public class GenerateEvent extends ModelObjectEvent {

	public GenerateEvent(Object object) {
		super(object);
	}

	@Override
	public EventType getEventType() {
		return EventType.GENERATE;
	}

}
