package fr.dush.slalomgenerator.events.model;

import lombok.Getter;

/**
 * Event on an specific object
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@Getter
public abstract class ModelObjectEvent extends ModelEvent {

	private Object object;

	public ModelObjectEvent(Object object) {
		super(object.getClass());
		this.object = object;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [clazz=" + getClazz() + "object=" + object + "]";
	}

}
