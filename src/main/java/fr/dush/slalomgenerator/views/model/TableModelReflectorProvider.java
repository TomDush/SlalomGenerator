package fr.dush.slalomgenerator.views.model;

import static com.google.common.collect.Lists.*;

import java.util.ResourceBundle;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.dush.slalomgenerator.dao.model.IFigureDAO;
import fr.dush.slalomgenerator.dao.model.IGeneratorParameterDAO;
import fr.dush.slalomgenerator.dao.model.ISequenceDAO;
import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.dto.model.GeneratorParameter;
import fr.dush.slalomgenerator.dto.model.Sequence;

@Component
public class TableModelReflectorProvider {

	@Inject
	private ResourceBundle bundle;

	@Inject
	private IGeneratorParameterDAO generatorParameterDAO;

	@Inject
	private IFigureDAO figureDAO;

	@Inject
	private ISequenceDAO sequenceDAO;

	@Bean(name = "modelGeneratorParameter")
	@Scope("prototype")
	public TableModelReflector<GeneratorParameter> generateParameterTableModel() {
		return new TableModelReflector<GeneratorParameter>(bundle, GeneratorParameter.class, newArrayList("name", "maxFigures", "turnbackNumber"),
				generatorParameterDAO.findAll());
	}

	@Bean(name = "modelSequence")
	@Scope("prototype")
	public TableModelReflector<Sequence> generateSequenceModel() {
		return new TableModelReflector<Sequence>(bundle, Sequence.class, newArrayList("name", "figuresSize"), sequenceDAO.findAll());
	}

	@Bean(name = "modelFigure")
	@Scope("prototype")
	public TableModelReflector<Figure> generateFigureModel() {
		final TableModelReflector<Figure> figureModel = new TableModelReflector<Figure>(bundle, Figure.class, newArrayList("name", "plotSize", "startingDirection",
				"inverseDirection", "aboutTurn"), figureDAO.findAll());

		// Set boolean properties...
		figureModel.setBooleanProperties(newArrayList("aboutTurn"));

		return figureModel;
	}
}
