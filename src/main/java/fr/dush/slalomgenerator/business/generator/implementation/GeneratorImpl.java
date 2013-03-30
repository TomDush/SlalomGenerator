package fr.dush.slalomgenerator.business.generator.implementation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dush.slalomgenerator.business.generator.IGenerator;
import fr.dush.slalomgenerator.dao.model.IFigureDAO;
import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.dto.model.GeneratorParameter;
import fr.dush.slalomgenerator.dto.model.Sequence;
import fr.dush.slalomgenerator.exceptions.GeneratorException;

@Named
public class GeneratorImpl implements IGenerator {

	private static final Logger LOGGER = LoggerFactory.getLogger(GeneratorImpl.class);

	private static final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh/mm/ss");

	@Inject
	private IFigureDAO figureDAO;

	@Override
	public Sequence generate(GeneratorParameter parameter) throws GeneratorException {
		LOGGER.info("Generating sequence with parameters : {}.", parameter);

		// ** Sort/distribute figures
		RandomSet randomSet = new RandomSet();
		for (Figure f : figureDAO.findAll()) {
			randomSet.add(f);
		}

		// ** Create sequence
		Sequence sequence = new Sequence("Generated : " + formatter.format(new Date()));
		SequenceStatus status = new SequenceStatus(sequence, parameter);

		// ** Generate figure list
		while (! status.isFinish()) {
			Figure next = null;

			if (status.haveToHalfturn()) next = randomSet.getHalfTurnRandomFigure(status.getDirection());
			else next = randomSet.getRandomFigure(status.getDirection());

			if (next == null) {
				String message = "Can't finish to generate sequence :  no figure starting " + status.getDirection();
				if (status.haveToHalfturn()) message += ", and with halfturn";

				LOGGER.error(message);
				throw new GeneratorException(sequence, "" + message);
			}

			status.addFigure(next);
		}

		return sequence;
	}

}
