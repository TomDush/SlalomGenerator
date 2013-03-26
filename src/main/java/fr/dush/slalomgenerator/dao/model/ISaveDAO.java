package fr.dush.slalomgenerator.dao.model;

import java.io.File;
import java.io.IOException;

import fr.dush.slalomgenerator.dto.model.Save;

/**
 * Save dao content into json file.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
public interface ISaveDAO {

	/**
	 * Save data in file
	 *
	 * @param file Existing file will be overrided.
	 * @throws IOException
	 */
	void saveIntoFile(File file) throws IOException;

	/**
	 * Load file, loaded data will be lost.
	 *
	 * @param file
	 * @return Return loaded parameters...
	 * @throws IOException
	 */
	Save loadFile(File file) throws IOException;

	/**
	 * Clear all configuration...
	 */
	void clear();

}
