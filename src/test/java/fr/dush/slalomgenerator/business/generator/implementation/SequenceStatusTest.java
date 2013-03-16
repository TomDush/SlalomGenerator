package fr.dush.slalomgenerator.business.generator.implementation;

import static fr.dush.slalomgenerator.engine.festassert.AssertionsSlalom.*;

import org.junit.Test;

import fr.dush.slalomgenerator.dto.enums.DIRECTION;
import fr.dush.slalomgenerator.dto.enums.DIRECTION_CHANGE;
import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.dto.model.GeneratorParameter;
import fr.dush.slalomgenerator.dto.model.Sequence;

public class SequenceStatusTest {

	@Test
	public void testFromInconsequential_with_chage() throws Exception {
		// Init sequence
		SequenceStatus status = new SequenceStatus(new Sequence(), new GeneratorParameter());
		status.addFigure(new Figure("init", 2, DIRECTION.FORWARD, DIRECTION_CHANGE.NEITHER, false));

		assertThat(status).hasDirection(DIRECTION.INCONSEQUENTIAL);

		// Add new figure start front and change
		status.addFigure(new Figure("change", 2, DIRECTION.FORWARD, DIRECTION_CHANGE.CHANGE, false));

		// Asserts
		assertThat(status).hasDirection(DIRECTION.BACKWARD);
	}
}
