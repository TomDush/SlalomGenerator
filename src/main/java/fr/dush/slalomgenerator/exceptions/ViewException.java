package fr.dush.slalomgenerator.exceptions;

/**
 * Exception thrown when error occur displaying view.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class ViewException extends RuntimeException {

	public ViewException() {
		super();
	}

	public ViewException(String message, Throwable cause) {
		super(message, cause);
	}

	public ViewException(String message) {
		super(message);
	}

	public ViewException(Throwable cause) {
		super(cause);
	}

}
