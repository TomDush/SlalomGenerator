package fr.dush.slalomgenerator.engine.festassert.asserts;

import static org.fest.assertions.api.Assertions.*;

import org.fest.assertions.api.AbstractAssert;

import fr.dush.slalomgenerator.business.generator.implementation.SequenceStatus;
import fr.dush.slalomgenerator.dto.enums.DIRECTION;

/**
 * Simple assertions for {@link SequenceStatus}...
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
public class SequenceStatusAssert extends AbstractAssert<SequenceStatusAssert, SequenceStatus> {

	public SequenceStatusAssert(SequenceStatus actual) {
		super(actual, SequenceStatusAssert.class);
	}

	public SequenceStatusAssert hasDirection(DIRECTION expected) {
		assertThat(actual.getDirection()).isEqualTo(expected);

		return this;
	}

	public SequenceStatusAssert hasHafturnCount(int expected) {
		assertThat(actual.getHafturnCount()).isEqualTo(expected);

		return this;
	}

	public SequenceStatusAssert hasPlot(int expected) {
		assertThat(actual.getPlot()).isEqualTo(expected);

		return this;
	}

}
