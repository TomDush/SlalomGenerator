package fr.dush.slalomgenerator.engine.mocks;

import static com.google.common.collect.Lists.*;

import javax.annotation.PostConstruct;

import fr.dush.slalomgenerator.dao.model.inmemory.SequenceDAOImpl;
import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.dto.model.Figures;
import fr.dush.slalomgenerator.dto.model.Sequence;

public class SequenceDAOMock extends SequenceDAOImpl {

	@PostConstruct
	public void populate() {
		Figure f1 = new Figure("first");
		Figure f2 = new Figure("second");

		Sequence s = new Sequence("MyName");
		s.setFigures(new Figures(newArrayList(f1, f2, f1)));

		save(new Sequence("First Sequence"));
		save(s);
	}
}
