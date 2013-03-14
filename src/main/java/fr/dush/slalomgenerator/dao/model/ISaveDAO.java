package fr.dush.slalomgenerator.dao.model;

import java.io.File;
import java.io.IOException;

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
	 * @throws IOException
	 */
	void loadFile(File file) throws IOException;

}
