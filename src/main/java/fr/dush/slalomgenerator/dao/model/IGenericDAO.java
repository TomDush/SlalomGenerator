package fr.dush.slalomgenerator.dao.model;

import java.util.List;

public interface IGenericDAO<Type> {

	/**
	 * Save (or update) object
	 *
	 * @param object
	 */
	void save(Type figure);

	/**
	 * Delete object
	 *
	 * @param object
	 */
	void delete(Type figure);

	/**
	 * Full objects list
	 *
	 * @return
	 */
	List<Type> findAll();

	/**
	 * Delete all objects
	 */
	void deleteAll();

	/**
	 * Restore objects lists (delete all before)
	 *
	 * @param objects
	 */
	void restore(List<Type> figures);
}
