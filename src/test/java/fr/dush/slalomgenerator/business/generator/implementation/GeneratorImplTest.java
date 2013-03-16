package fr.dush.slalomgenerator.business.generator.implementation;

import static com.google.common.collect.Lists.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dush.slalomgenerator.dao.model.IFigureDAO;
import fr.dush.slalomgenerator.dto.enums.DIRECTION;
import fr.dush.slalomgenerator.dto.enums.DIRECTION_CHANGE;
import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.dto.model.GeneratorParameter;
import fr.dush.slalomgenerator.dto.model.Sequence;
import fr.dush.slalomgenerator.engine.AbstractJunitTest;

public class GeneratorImplTest extends AbstractJunitTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorImplTest.class);

	@InjectMocks
	private GeneratorImpl generatorImpl;

	@Mock
	private IFigureDAO figureDAO;

	@Test
	public void testGenerate() throws Exception {
		// ** Init figures ...
		List<Figure> figures = newArrayListWithCapacity(10);
		figures.add(new Figure("Front simple", 2, DIRECTION.FORWARD, DIRECTION_CHANGE.KEEP, false, 1));
		figures.add(new Figure("Back simple", 2, DIRECTION.BACKWARD, DIRECTION_CHANGE.KEEP, false, 1));
		figures.add(new Figure("Crabe", 4, DIRECTION.INCONSEQUENTIAL, DIRECTION_CHANGE.NEITHER, false));
		figures.add(new Figure("Front halfturn", 2, DIRECTION.FORWARD, DIRECTION_CHANGE.KEEP, true));
		figures.add(new Figure("Back halfturn", 2, DIRECTION.BACKWARD, DIRECTION_CHANGE.CHANGE, true));
		figures.add(new Figure("Front reverse", 2, DIRECTION.FORWARD, DIRECTION_CHANGE.CHANGE, false, 2));
		figures.add(new Figure("Back reverse", 2, DIRECTION.BACKWARD, DIRECTION_CHANGE.CHANGE, false, 2));

		when(figureDAO.findAll()).thenReturn(figures);

		// ** Test parameters
		GeneratorParameter param = new GeneratorParameter();
		param.setMaxFigures(50);
		param.setPlotNumber(20);
		param.setTurnbackInLastPlot(3);
		param.setTurnbackNumber(2);

		// ** EXEC
		Sequence sequence = generatorImpl.generate(param);

		// ** Show :
		LOGGER.info("Generated figure : ");
		for (Figure f : sequence.getFigures()) {
			LOGGER.info("\t- {}", f);
		}
	}
}
