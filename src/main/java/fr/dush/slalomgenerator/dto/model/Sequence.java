package fr.dush.slalomgenerator.dto.model;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Figures sequence.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "creationDate")
public class Sequence {

	private Date creationDate = new Date();

	/** Sequence's name (and ID) */
	private String name;

	/** Sorted figures list */
	private Figures figures = new Figures();

	public Sequence(String name) {
		this.name = name;
	}

	public int getFiguresSize() {
		return figures.size();
	}

}
