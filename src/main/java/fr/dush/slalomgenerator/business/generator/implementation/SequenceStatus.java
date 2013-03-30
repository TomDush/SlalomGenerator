package fr.dush.slalomgenerator.business.generator.implementation;

import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dush.slalomgenerator.dto.enums.DIRECTION;
import fr.dush.slalomgenerator.dto.enums.DIRECTION_CHANGE;
import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.dto.model.GeneratorParameter;
import fr.dush.slalomgenerator.dto.model.Sequence;

@Getter
public class SequenceStatus {

	private static final Logger LOGGER = LoggerFactory.getLogger(SequenceStatus.class);

	/** Sequence to complete */
	private Sequence sequence;

	/** Generation parameters */
	private GeneratorParameter parameters;

	/** Rider position */
	private int plot = 0;

	/** Direction (referential = earth) */
	private int hafturnCount = 0;

	/** Rider direction (referential = rider) */
	@Getter
	private DIRECTION direction = DIRECTION.FORWARD;

	public SequenceStatus(Sequence sequence, GeneratorParameter parameters) {
		this.sequence = sequence;
		this.parameters = parameters;
	}

	/**
	 * Verify if sequence is finish.
	 *
	 * @return
	 */
	public boolean isFinish() {
		return parameters.getMaxFigures() <= sequence.getFigures().size() || hafturnCount > parameters.getTurnbackNumber();
	}

	public boolean haveToHalfturn() {
		if (hafturnCount % 2 == 0) {
			return plot >= parameters.getPlotNumber() - parameters.getTurnbackInLastPlot();
		}

		return plot <= parameters.getTurnbackInLastPlot();
	}

	/**
	 * Add figure to sequence and simulate rider position and direction.
	 *
	 * @param figure
	 */
	public void addFigure(Figure figure) {
		LOGGER.debug("Initial position : plot={} ; direction={} ; hafturnCount={}", new Object[] { plot, direction, hafturnCount });
		LOGGER.debug("Add figure : {}.", figure);

		// Adding figure to sequence
		sequence.getFigures().add(figure);

		// Rider direction
		if (figure.getInverseDirection() == DIRECTION_CHANGE.NEITHER) {
			// Whatever initial direction, new direction is INCONSEQUENTIAL
			direction = DIRECTION.INCONSEQUENTIAL;

		} else if (DIRECTION.INCONSEQUENTIAL == direction) {
			// direction depends of figure starting and if it's inverse
			direction = figure.getStartingDirection();
			if (figure.getInverseDirection() == DIRECTION_CHANGE.CHANGE) direction = direction.inverse();

		} else if (figure.getInverseDirection() == DIRECTION_CHANGE.CHANGE) {
			direction = direction.inverse();

		}

		// Rider position
		plot += figure.getPlotSize() * (hafturnCount % 2 == 0 ? 1 : -1);

		// Half-Turn
		if (figure.isAboutTurn()) hafturnCount++;
	}

}