package fr.dush.slalomgenerator.dao.model;

import java.util.Collection;
import java.util.List;

import fr.dush.slalomgenerator.dto.model.GeneratorParameter;

/**
 * Access to persisted GeneratorParameter.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 * @see fr.dush.slalomgenerator.dao.model.inmemory.GeneratorParameterDAOImpl
 *
 */
public interface IGeneratorParameterDAO {

	/**
	 * To be saved, {@link GeneratorParameter}'s name must be filled.
	 *
	 * @param param GeneratorParameter with named filled.
	 */
	void save(GeneratorParameter param);

	void delete(GeneratorParameter param);

	List<GeneratorParameter> findAll();

	/**
	 * Replace existing GeneratorParameter by those given.
	 *
	 * @param parameters New parameters.
	 */
	void restore(Collection<GeneratorParameter> parameters);
}
