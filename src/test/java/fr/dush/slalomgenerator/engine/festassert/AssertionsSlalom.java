package fr.dush.slalomgenerator.engine.festassert;

import org.fest.assertions.api.Assertions;

import fr.dush.slalomgenerator.business.generator.implementation.SequenceStatus;
import fr.dush.slalomgenerator.engine.festassert.asserts.SequenceStatusAssert;

public class AssertionsSlalom extends Assertions {

	public static SequenceStatusAssert assertThat(SequenceStatus actual) {
		return new SequenceStatusAssert(actual);
	}
}
