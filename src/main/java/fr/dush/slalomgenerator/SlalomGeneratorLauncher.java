package fr.dush.slalomgenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import fr.dush.slalomgenerator.views.IView;

/**
 * Slalom genrator's entry point...
 *
 * @author Thomas Duchatelle
 */
public class SlalomGeneratorLauncher {

	private static final Logger LOGGER = LoggerFactory.getLogger(SlalomGeneratorLauncher.class);

	/**
	 * Application main method. No args expected...
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// ** Create spring context
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/slalomgenerator-context.xml");

		try {
			// Display view
			final IView view = applicationContext.getBean(IView.class);
			view.displayHome();

			// Wait end and close application
			view.waitEndProgram();
		} catch (InterruptedException e) {
			LOGGER.info("Application was interrupted");
		}

	}

}
