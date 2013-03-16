package fr.dush.slalomgenerator.exceptions;

import lombok.Getter;
import fr.dush.slalomgenerator.dto.model.Sequence;

/**
 * Exception throws when generator can't finish sequence.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class GeneratorException extends Exception {

	@Getter
	private Sequence unfinishSequence;

	public GeneratorException(Sequence sequence, String message) {
		super(message);
	}

}
