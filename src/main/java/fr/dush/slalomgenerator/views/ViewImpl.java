package fr.dush.slalomgenerator.views;


import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import fr.dush.slalomgenerator.exceptions.ViewException;
import fr.dush.slalomgenerator.views.pages.HomePage;
import fr.dush.slalomgenerator.views.utils.Handler;
import fr.dush.slalomgenerator.views.utils.WindowWaiter;

@Named
public class ViewImpl implements IView {

	private static final Logger LOGGER = LoggerFactory.getLogger(ViewImpl.class);

	@Inject
	private ApplicationContext applicationContext;

	private JFrame rootFrame;

	@PostConstruct
	public void initExceptionHandler() {
		System.setProperty("sun.awt.exception.handler", Handler.class.getName());
	}

	@Override
	public void displayHome() {
		LOGGER.info("Display interface...");
		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					Thread.setDefaultUncaughtExceptionHandler(applicationContext.getBean(Handler.class));

					rootFrame = applicationContext.getBean(HomePage.class);
					rootFrame.setVisible(true);
				}
			});
		} catch (Exception e) {
			throw new ViewException("Error while loading home page.", e);
		}

	}

	@Override
	public void waitEndProgram() throws InterruptedException {
		LOGGER.info("Waiting end program ...");
		// Thread.sleep(50000);

		// Create listener which will notify himself when main frame will be closed.
		if (null != rootFrame) new WindowWaiter(rootFrame).waitClosed();

		LOGGER.info("Program endded");

	}

	@Override
	public void quit() {
		rootFrame.dispose();
	}

}
