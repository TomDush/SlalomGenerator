package fr.dush.slalomgenerator.dao.model.inmemory;

import static org.fest.assertions.api.Assertions.*;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import fr.dush.slalomgenerator.dao.model.IFigureDAO;
import fr.dush.slalomgenerator.dto.enums.DIRECTION;
import fr.dush.slalomgenerator.dto.enums.DIRECTION_CHANGE;
import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.engine.AbstractJunitTest;


public class FigureDAOImplTest extends AbstractJunitTest{

	@Inject
	private IFigureDAO figureDAO;

	@Before
	public void clear() {
		figureDAO.deleteAll();
	}

	@Test
	public void testSave() throws Exception {
		assertThat(figureDAO.findAll()).isEmpty();

		Figure f = generateFigure();
		figureDAO.save(f);

		assertThat(figureDAO.findAll()).containsOnly(f).hasSize(1);
	}

	@Test
	public void testDelete() throws Exception {
		// Préparation
		Figure f = generateFigure();
		figureDAO.save(f);

		assertThat(figureDAO.findAll()).containsOnly(f).hasSize(1);

		// Suppression non existante
		figureDAO.delete(generateOtherFigure());
		assertThat(figureDAO.findAll()).containsOnly(f).hasSize(1);

		// Suppression existante
		figureDAO.delete(f);
		assertThat(figureDAO.findAll()).isEmpty();
	}

	@Test
	public void testDeleteAll() throws Exception {
		figureDAO.save(generateFigure());
		figureDAO.save(generateOtherFigure());

		assertThat(figureDAO.findAll()).hasSize(2);

		figureDAO.deleteAll();
		assertThat(figureDAO.findAll()).isEmpty();
	}

	public static Figure generateFigure() {
		Figure f = new Figure();
		f.setName("snake");
		f.setPlotSize(5);
		f.setDescription("Left roller folow right roller...");
		f.setInverseDirection(DIRECTION_CHANGE.KEEP);
		f.setStartingDirection(DIRECTION.FORWARD);
		f.setAboutTurn(false);

		return f;
	}

	public static Figure generateOtherFigure() {
		Figure f = new Figure();
		f.setName("backreverse");
		f.setPlotSize(1);
		f.setDescription("Changing direction, keeping look on same side");
		f.setInverseDirection(DIRECTION_CHANGE.CHANGE);
		f.setStartingDirection(DIRECTION.BACKWARD);
		f.setAboutTurn(true);

		return f;
	}
}
