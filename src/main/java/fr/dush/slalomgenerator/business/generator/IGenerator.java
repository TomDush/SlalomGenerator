package fr.dush.slalomgenerator.business.generator;


import fr.dush.slalomgenerator.dto.model.GeneratorParameter;
import fr.dush.slalomgenerator.dto.model.Sequence;
import fr.dush.slalomgenerator.exceptions.GeneratorException;

/**
 * Sequence's generator : generate random slalon sequence.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
public interface IGenerator {

	/**
	 * Generate figures sequence corresponding to given paramters.
	 *
	 * @param parameter Generation parameters
	 * @return New sequence !
	 * @throws GeneratorException
	 */
	Sequence generate(GeneratorParameter parameter) throws GeneratorException;

}
