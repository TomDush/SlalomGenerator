package fr.dush.slalomgenerator.business.generator.implementation;

import static com.google.common.collect.Lists.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.ListMultimap;

import fr.dush.slalomgenerator.dto.enums.DIRECTION;
import fr.dush.slalomgenerator.dto.model.Figure;

/**
 * Sort figure by starting direction and type (keep direction, or half-turn. Provide {@link #getRandomFigure(DIRECTION)} to get random
 * figure with good direction.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
public class RandomSet {

	private static final Logger LOGGER = LoggerFactory.getLogger(RandomSet.class);

	/** Figures starting forward or starting in reverse */
	private ListMultimap<DIRECTION, Figure> figures = ArrayListMultimap.create();

	/** Figures making halfturn */
	private List<Figure> halfturnFigures = newArrayList();

	/**
	 * Distribute figures in appropriate list.
	 *
	 * <p>
	 * Each figure is added {@link Figure#getProbability()} times. Either in a single list (2x), or in both (1 time each).
	 * </p>
	 *
	 * @param f Figure to add.
	 */
	public void add(Figure f) {
		LOGGER.debug("Add figure to random set : {}", f);

		if (f.isAboutTurn()) {
			// ** Half turn figures are put in separate list.
			addMultiTimes(halfturnFigures, f, f.getProbability());

		} else {

			// ** Other figures are distributed in forwardFigures list and backwardFigures list.
			addInDirection(f, DIRECTION.FORWARD);
			addInDirection(f, DIRECTION.BACKWARD);
		}
	}

	/**
	 * Get random figure which start by direction's parameter
	 *
	 * @param direction FORWARD or BACKWARD or INCONSEQUENTIAL
	 * @return
	 */
	public Figure getRandomFigure(DIRECTION direction) {
		// ** Get compatible figures list
		final List<Figure> list = figures.get(direction);

		// ** Init full list if necessary
		if (list.isEmpty() && direction == DIRECTION.INCONSEQUENTIAL) {
			list.addAll(figures.values());
		}

		// ** Return random figure
		return returnRandomFigure(list);
	}

	/**
	 * Return random haf-turn figure.
	 *
	 * @param direction
	 * @return
	 */
	public Figure getHalfTurnRandomFigure(DIRECTION direction) {
		// ** Get compatible figures list
		final List<Figure> list = newArrayList(Collections2.filter(halfturnFigures, generateDirectionFilter(direction)));

		// ** Return random figure
		return returnRandomFigure(list);
	}

	/**
	 * Return random figure present in list
	 * @param list
	 * @return
	 */
	private Figure returnRandomFigure(final List<Figure> list) {
		if (null == list || list.isEmpty()) return null;

		return list.get((int) (Math.random() * list.size()));
	}

	private Predicate<Figure> generateDirectionFilter(final DIRECTION direction) {
		return new Predicate<Figure>() {
			@Override
			public boolean apply(Figure figure) {
				return direction.isCompatible(figure.getStartingDirection());
			}
		};
	}

	/**
	 * If figure starting by direction, add figure to direction's list. 2 times if figure is not compatible with other side.
	 *
	 * @param figure
	 * @param direction FORWARD or BACKWARD
	 */
	private void addInDirection(Figure figure, DIRECTION direction) {
		if (figure.getStartingDirection().isCompatible(direction)) {

			final int probability = figure.getStartingDirection().isCompatible(direction.inverse()) ? figure.getProbability() : figure.getProbability() * 2;
			addMultiTimes(figures.get(direction), figure, probability);
		}
	}

	/**
	 * Adding n times same figure in list.
	 *
	 * @param figuresList
	 * @param figure
	 * @param times
	 */
	protected void addMultiTimes(List<Figure> figuresList, Figure figure, int times) {
		for (int i = 0; i < times; i++) {
			figuresList.add(figure);
		}
	}
}