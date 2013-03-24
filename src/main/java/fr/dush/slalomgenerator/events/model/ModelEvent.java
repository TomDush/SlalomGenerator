package fr.dush.slalomgenerator.events.model;

import lombok.Getter;
import fr.dush.slalomgenerator.events.UIEvent;

/**
 * Event on model : persistent object change.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@Getter
public abstract class ModelEvent extends UIEvent {

	protected Class<?> clazz;

	public ModelEvent(Class<?> clazz) {
		this.clazz = clazz;
	}

	public abstract EventType getEventType();

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [clazz=" + clazz + "]";
	}

}
