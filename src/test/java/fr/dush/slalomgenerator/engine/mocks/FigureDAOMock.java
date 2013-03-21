package fr.dush.slalomgenerator.engine.mocks;

import javax.annotation.PostConstruct;

import fr.dush.slalomgenerator.dao.model.inmemory.FigureDAOImpl;
import fr.dush.slalomgenerator.dto.enums.DIRECTION;
import fr.dush.slalomgenerator.dto.enums.DIRECTION_CHANGE;
import fr.dush.slalomgenerator.dto.model.Figure;

public class FigureDAOMock extends FigureDAOImpl {

	@PostConstruct
	public void populate() {
		save(new Figure("Front simple", 2, DIRECTION.FORWARD, DIRECTION_CHANGE.KEEP, false, 1));
		save(new Figure("Back simple", 2, DIRECTION.BACKWARD, DIRECTION_CHANGE.KEEP, false, 1));
		save(new Figure("Crabe", 4, DIRECTION.INCONSEQUENTIAL, DIRECTION_CHANGE.NEITHER, false));
		save(new Figure("Front halfturn", 2, DIRECTION.FORWARD, DIRECTION_CHANGE.KEEP, true));
		save(new Figure("Back halfturn", 2, DIRECTION.BACKWARD, DIRECTION_CHANGE.CHANGE, true));
		save(new Figure("Front reverse", 2, DIRECTION.FORWARD, DIRECTION_CHANGE.CHANGE, false, 2));
		save(new Figure("Back reverse", 2, DIRECTION.BACKWARD, DIRECTION_CHANGE.CHANGE, false, 2));
	}
}
