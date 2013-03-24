package fr.dush.slalomgenerator.exceptions;

/**
 * Unchecked exception throw when failed to instanciate Event
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 * @see fr.dush.slalomgenerator.views.utils.EventUtils
 *
 */
@SuppressWarnings("serial")
public class EventCreation extends RuntimeException {

	public EventCreation() {
		super();
	}

	public EventCreation(String message, Throwable cause) {
		super(message, cause);
	}

	public EventCreation(String message) {
		super(message);
	}

	public EventCreation(Throwable cause) {
		super(cause);
	}

}
