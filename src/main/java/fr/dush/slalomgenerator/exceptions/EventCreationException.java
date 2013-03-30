package fr.dush.slalomgenerator.exceptions;

/**
 * Unchecked exception throw when failed to instanciate Event
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 * @see fr.dush.slalomgenerator.views.utils.EventUtils
 *
 */
@SuppressWarnings("serial")
public class EventCreationException extends RuntimeException {

	public EventCreationException() {
		super();
	}

	public EventCreationException(String message, Throwable cause) {
		super(message, cause);
	}

	public EventCreationException(String message) {
		super(message);
	}

	public EventCreationException(Throwable cause) {
		super(cause);
	}

}
