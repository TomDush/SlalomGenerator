package fr.dush.slalomgenerator.dao.model;

import fr.dush.slalomgenerator.dto.model.GeneratorParameter;

/**
 * Access to persisted GeneratorParameter.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 * @see fr.dush.slalomgenerator.dao.model.inmemory.GeneratorParameterDAOImpl
 *
 */
public interface IGeneratorParameterDAO extends IGenericDAO<GeneratorParameter> {

	/**
	 * To be saved, {@link GeneratorParameter}'s name must be filled.
	 *
	 * @param param GeneratorParameter with named filled.
	 */
	@Override
	void save(GeneratorParameter param);
}
