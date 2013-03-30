package fr.dush.slalomgenerator.events.generic;

import lombok.Getter;
import lombok.Setter;
import fr.dush.slalomgenerator.events.UIEvent;

/**
 * User error
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@Getter
@Setter
public class FunctionalErrorEvent extends UIEvent {

	private String message;

	public FunctionalErrorEvent(Object source, String message) {
		super(source);
		this.message = message;
	}
}
