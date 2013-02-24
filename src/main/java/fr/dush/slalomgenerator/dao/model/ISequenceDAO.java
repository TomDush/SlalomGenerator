package fr.dush.slalomgenerator.dao.model;

import java.util.List;

import fr.dush.slalomgenerator.dto.model.Sequence;

/**
 * Access to saved sequence objects
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 * @see fr.dush.slalomgenerator.dao.model.implementations.SequenceDAOImpl
 *
 */
public interface ISequenceDAO {

	/**
	 * Save (or update) sequence
	 *
	 * @param figure
	 */
	void save(Sequence sequence);

	/**
	 * Delete sequence
	 *
	 * @param figure
	 */
	void delete(Sequence figure);

	/**
	 * Full sequence list, sorted by name.
	 *
	 * @return
	 */
	List<Sequence> findAll();

	/**
	 * Delete all sequences
	 */
	void deleteAll();

	/**
	 * Restore sequence list (delete all before)
	 *
	 * @param sequences
	 */
	void restore(List<Sequence> sequences);

}
