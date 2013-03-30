package fr.dush.slalomgenerator.dao.model.file;

import static com.google.common.collect.Lists.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.Maps;

import fr.dush.slalomgenerator.dao.model.IFigureDAO;
import fr.dush.slalomgenerator.dao.model.IGeneratorParameterDAO;
import fr.dush.slalomgenerator.dao.model.ISaveDAO;
import fr.dush.slalomgenerator.dao.model.ISequenceDAO;
import fr.dush.slalomgenerator.dao.utils.FigureListSerializer;
import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.dto.model.Figures;
import fr.dush.slalomgenerator.dto.model.Save;
import fr.dush.slalomgenerator.dto.model.Sequence;

@Named
public class SaveDAOImpl implements ISaveDAO {

	@Inject
	private ObjectMapper objectMapper;

	@Inject
	private IFigureDAO figureDAO;

	@Inject
	private ISequenceDAO sequenceDAO;

	@Inject
	private IGeneratorParameterDAO generatorParameterDAO;

	@Inject
	private FigureListSerializer serializer;

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void saveIntoFile(File file) throws IOException {

		final List<Sequence> sequences = sequenceDAO.findAll();

		// Save all sequence's figures
		for (Sequence s : sequences) {
			for (Figure f : s.getFigures()) {
				figureDAO.save(f);
			}
		}

		// Create save
		Save save = new Save(figureDAO.findAll(), sequences, generatorParameterDAO.findAll());

		objectMapper.writeValue(file, save);
	}

	@Override
	public Save loadFile(File file) throws IOException {
		Save save = objectMapper.readValue(file, new TypeReference<Save>() {
		});

		// Restore references...
		Map<String, Figure> figures = parseFigures(save.getFigures());
		for (Sequence s : save.getSequences()) {
			restoreReferences(s, figures);
		}

		// Initialize DAOs
		figureDAO.restore(save.getFigures());
		sequenceDAO.restore(save.getSequences());
		generatorParameterDAO.restore(save.getGeneratorParameters());

		return save;
	}

	@Override
	public void clear() {
		figureDAO.deleteAll();
		sequenceDAO.deleteAll();
		generatorParameterDAO.deleteAll();
	}

	/**
	 * Replace sequence's reference to figures by good ones (from figures list).
	 *
	 * @param s
	 * @param figures
	 */
	private void restoreReferences(Sequence s, final Map<String, Figure> figures) {
		s.setFigures(new Figures(transform(s.getFigures(), new Function<Figure, Figure>() {

			@Override
			public Figure apply(Figure input) {
				Figure f = figures.get(input.getName());

				return null == f ? input : f;
			}
		})));
	}

	/**
	 * Create new map with figure's name as key.
	 *
	 * @param save
	 * @return
	 */
	private Map<String, Figure> parseFigures(List<Figure> figuresList) {
		Map<String, Figure> figures = Maps.newHashMapWithExpectedSize(figuresList.size());
		for (Figure f : figuresList) {
			figures.put(f.getName(), f);
		}

		return figures;
	}

}
