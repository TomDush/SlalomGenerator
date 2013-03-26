package fr.dush.slalomgenerator.events.generic;

import lombok.Getter;
import lombok.Setter;
import fr.dush.slalomgenerator.events.UIEvent;

public class QuitEvent extends UIEvent {

	@Getter
	@Setter
	private boolean confirmed = false;

	public QuitEvent() {
	}

	public QuitEvent(Object source) {
		super(source);
	}

}
