package fr.dush.slalomgenerator.dto.model;

import lombok.Data;

/**
 * Parameters provided to sequence generator.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@Data
public class GeneratorParameter {

	/** Primary key if it persisted */
	private String name;

	/** Number of plot */
	private int plotNumber = 20;

	/** Expected turn back in sequence */
	private int turnbackNumber = 0;

	/** Turn back may occur between end and this plot number (from end) */
	private int turnbackInLastPlot = 5;

	/** Sequence */
	private int maxFigures = 10;
}
