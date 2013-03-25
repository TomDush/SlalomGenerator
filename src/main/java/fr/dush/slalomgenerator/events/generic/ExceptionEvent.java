package fr.dush.slalomgenerator.events.generic;

import lombok.Getter;
import fr.dush.slalomgenerator.events.UIEvent;

public class ExceptionEvent extends UIEvent {

	@Getter
	private Throwable throwable;

	public ExceptionEvent(Object source, Throwable throwable) {
		super(source);
		this.throwable = throwable;
	}

}
