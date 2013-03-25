package fr.dush.slalomgenerator.views;

/**
 * Display and control an interface.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
public interface IView {

	/**
	 * Display interface
	 */
	void displayHome();

	/**
	 * Blocked method : waiting program terminated.
	 * @throws InterruptedException
	 */
	void waitEndProgram() throws InterruptedException;

	/**
	 * Quit program.
	 */
	void quit();
}
