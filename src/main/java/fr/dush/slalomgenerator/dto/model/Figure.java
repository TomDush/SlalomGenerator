package fr.dush.slalomgenerator.dto.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import fr.dush.slalomgenerator.dto.enums.DIRECTION;
import fr.dush.slalomgenerator.dto.enums.DIRECTION_CHANGE;

/**
 * Slalom figure, element of sequence.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 */
@Data
@NoArgsConstructor
public class Figure {

	/** Figure name (index) */
	private String name;

	/** Plot number to realize figure */
	private int plotSize;

	/** Figure's description */
	private String description;

	/** Direction to realize figure */
	private DIRECTION startingDirection = DIRECTION.FORWARD;

	/** Direction is inversed after figure */
	private DIRECTION_CHANGE inverseDirection = DIRECTION_CHANGE.KEEP;

	/** Rider return back */
	private boolean aboutTurn = false;

	/** Probability to obtain this figure in sequence */
	private int probability = 1;

	public Figure(String name) {
		this.name = name;
	}

	public Figure(String name, int plotSize, DIRECTION startingDirection, DIRECTION_CHANGE inverseDirection, boolean aboutTurn) {
		this(name, plotSize, startingDirection, inverseDirection, aboutTurn, 1);
	}

	public Figure(String name, int plotSize, DIRECTION startingDirection, DIRECTION_CHANGE inverseDirection, boolean aboutTurn, int probability) {
		this.name = name;
		this.plotSize = plotSize;
		this.startingDirection = startingDirection;
		this.inverseDirection = inverseDirection;
		this.aboutTurn = aboutTurn;
		this.probability = probability;
	}

}
