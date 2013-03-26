package fr.dush.slalomgenerator.events.model;

import java.util.List;

import lombok.Getter;

@Getter
public class FullChangeModel<Type> extends ModelEvent {

	/** New object list, cancel and replace last one */
	private List<Type> list;

	public FullChangeModel(Class<Type> clazz, List<Type> list) {
		super(clazz);
		this.list = list;
	}

	@Override
	public EventType getEventType() {
		return EventType.FULL_CHANGE;
	}

}
