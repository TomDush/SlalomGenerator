package fr.dush.slalomgenerator.engine.mocks;

import javax.annotation.PostConstruct;

import fr.dush.slalomgenerator.dao.model.inmemory.GeneratorParameterDAOImpl;
import fr.dush.slalomgenerator.dto.model.GeneratorParameter;

public class GeneratorParameterDAOMock extends GeneratorParameterDAOImpl {

	@PostConstruct
	public void populate() {

		GeneratorParameter param = new GeneratorParameter();
		param.setMaxFigures(50);
		param.setPlotNumber(20);
		param.setTurnbackInLastPlot(3);
		param.setTurnbackNumber(2);

		save(param);

		param = new GeneratorParameter();
		param.setMaxFigures(20);
		param.setPlotNumber(20);
		param.setTurnbackInLastPlot(5);
		param.setTurnbackNumber(0);

		save(param);
	}
}
