package fr.dush.slalomgenerator.dto.model;

import static com.google.common.collect.Lists.*;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data's save, can be write into json file.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@Data
@NoArgsConstructor
public class Save {

	private Date creation = new Date();

	private List<Figure> figures = newArrayList();

	public Save(List<Figure> figures) {
		this.figures = figures;
	}



}
