package fr.dush.slalomgenerator.events;

import lombok.Getter;
import lombok.Setter;

/**
 * Generic UI event...
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */

public abstract class UIEvent {

	@Getter
	@Setter
	private Object source;

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
