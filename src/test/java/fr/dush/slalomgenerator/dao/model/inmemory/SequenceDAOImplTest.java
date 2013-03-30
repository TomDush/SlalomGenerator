package fr.dush.slalomgenerator.dao.model.inmemory;

import static com.google.common.collect.Lists.*;
import static org.fest.assertions.api.Assertions.*;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import fr.dush.slalomgenerator.dao.model.ISequenceDAO;
import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.dto.model.Figures;
import fr.dush.slalomgenerator.dto.model.Sequence;
import fr.dush.slalomgenerator.engine.AbstractJunitTest;

public class SequenceDAOImplTest extends AbstractJunitTest {

	@Inject
	private ISequenceDAO sequenceDAO;

	@Before
	public void clear() {
		sequenceDAO.deleteAll();
	}

	@Test
	public void testSaveInError() throws Exception {
		try {
			sequenceDAO.save(new Sequence());
			failBecauseExceptionWasNotThrown(IllegalArgumentException.class);

		} catch (Exception e) {
			assertThat(e).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("must be filled");
		}
	}

	@Test
	public void testSave() throws Exception {
		assertThat(sequenceDAO.findAll()).isEmpty();

		// ** Data preparation
		Figure f1 = new Figure("first");
		Figure f2 = new Figure("second");

		Sequence s = new Sequence("MyName");
		s.setFigures(new Figures(newArrayList(f1, f2, f1)));

		// ** Exec
		sequenceDAO.save(s);

		// Tests
		assertThat(sequenceDAO.findAll()).hasSize(1).containsOnly(s);
	}
}
