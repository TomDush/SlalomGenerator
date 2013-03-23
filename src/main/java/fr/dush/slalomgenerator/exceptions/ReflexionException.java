package fr.dush.slalomgenerator.exceptions;

@SuppressWarnings("serial")
public class ReflexionException extends RuntimeException {

	public ReflexionException() {
	}

	public ReflexionException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReflexionException(String message) {
		super(message);
	}

	public ReflexionException(Throwable cause) {
		super(cause);
	}

}
