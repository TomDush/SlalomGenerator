package fr.dush.slalomgenerator.dao.model.inmemory;

import static com.google.common.collect.Lists.*;
import static fr.dush.slalomgenerator.dao.model.inmemory.FigureDAOImplTest.*;
import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.File;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.dush.slalomgenerator.dao.model.IFigureDAO;
import fr.dush.slalomgenerator.dao.model.IGeneratorParameterDAO;
import fr.dush.slalomgenerator.dao.model.ISequenceDAO;
import fr.dush.slalomgenerator.dao.utils.FigureListSerializer;
import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.dto.model.Figures;
import fr.dush.slalomgenerator.dto.model.GeneratorParameter;
import fr.dush.slalomgenerator.dto.model.Save;
import fr.dush.slalomgenerator.dto.model.Sequence;
import fr.dush.slalomgenerator.engine.AbstractJunitTest;

public class SaveDAOImplTest extends AbstractJunitTest {

	@InjectMocks
	private SaveDAOImpl databaseSaver;

	@Mock
	private IFigureDAO figureDAO;

	@Mock
	private ISequenceDAO sequenceDAO;

	@Mock
	private IGeneratorParameterDAO generatorParameterDAO;

	@Inject
	@Spy
	private FigureListSerializer figureListSerializer;

	/** DO NOT @SPY : Json Configuration doesn't work if it's proxyfied by mockito. */
	@Inject
	private ObjectMapper objectMapper;

	@Before
	public void settingMapper() {
		if (databaseSaver instanceof SaveDAOImpl) {
			databaseSaver.setObjectMapper(objectMapper);
		}
	}

	@Test
	public void testSaveFileAndRead() throws Exception {
		// ** POPULATE DATA BASE
		when(figureDAO.findAll()).thenReturn(newArrayList(generateFigure(), generateOtherFigure(), new Figure("first"), new Figure("second")));
		when(sequenceDAO.findAll()).thenReturn(newArrayList(generateSequence()));
		when(generatorParameterDAO.findAll()).thenReturn(newArrayList(generateParameter()));

		File file = new File("target/outputFile.json");
		if (file.exists()) file.delete();

		// Saving
		databaseSaver.saveIntoFile(file);

		// Testing ...
		assertThat(file).exists();
		verify(figureDAO).findAll();
		verify(sequenceDAO).findAll();
		verify(generatorParameterDAO).findAll();

		verify(figureDAO, times(2)).save(new Figure("first")); // figure present 2 times in sequence
		verify(figureDAO).save(new Figure("second")); // fiugure present 1 time
		verifyNoMoreInteractions(figureDAO, sequenceDAO, generatorParameterDAO);

		Save save = objectMapper.readValue(file, Save.class);
		assertThat(save).isNotNull();
		assertThat(save.getFigures()).containsOnly(generateFigure(), generateOtherFigure(), new Figure("first"), new Figure("second"));
		assertThat(save.getSequences()).containsOnly(generateSequence());
		assertThat(save.getGeneratorParameters()).containsOnly(generateParameter());

	}

	@Test
	public void testRead() throws Exception {
		File file = new File("src/test/resources/save_file.json");
		assertThat(file).exists();

		// Exec
		databaseSaver.loadFile(file);

		// Testing ...
		verify(figureDAO).restore(newArrayList(generateFigure(), generateOtherFigure(), new Figure("first"), new Figure("second")));
		verify(sequenceDAO).restore(newArrayList(generateSequence()));
		verify(generatorParameterDAO).restore(newArrayList(generateParameter()));

	}

	private static GeneratorParameter generateParameter() {
		GeneratorParameter p = new GeneratorParameter();

		p.setName("Classic");
		p.setMaxFigures(40);
		p.setPlotNumber(25);
		p.setTurnbackInLastPlot(5);
		p.setTurnbackNumber(3);

		return p;
	}

	private static Sequence generateSequence() {
		Figure f1 = new Figure("first");
		Figure f2 = new Figure("second");

		Sequence s = new Sequence("Hello");
		s.setFigures(new Figures(newArrayList(f1, f2, f1)));

		return s;
	}
}
