package fr.dush.slalomgenerator.dao.model;

import java.util.List;

import fr.dush.slalomgenerator.dto.model.Figure;

/**
 * Access to saved figure objects
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 * @see fr.dush.slalomgenerator.dao.model.inmemory.FigureDAOImpl
 *
 */
public interface IFigureDAO {

	/**
	 * Save (or update) figure
	 *
	 * @param figure
	 */
	void save(Figure figure);

	/**
	 * Delete figure
	 *
	 * @param figure
	 */
	void delete(Figure figure);

	/**
	 * Full figure list, sorted by name.
	 *
	 * @return
	 */
	List<Figure> findAll();

	/**
	 * Delete all figures
	 */
	void deleteAll();

	/**
	 * Restore figure list (delete all before)
	 *
	 * @param figures
	 */
	void restore(List<Figure> figures);

}
