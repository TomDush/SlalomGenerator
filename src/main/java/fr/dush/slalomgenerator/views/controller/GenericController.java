package fr.dush.slalomgenerator.views.controller;

import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import fr.dush.slalomgenerator.events.configuration.SaveConfigurationEvent;
import fr.dush.slalomgenerator.events.generic.AboutEvent;
import fr.dush.slalomgenerator.events.generic.ExceptionEvent;
import fr.dush.slalomgenerator.events.generic.QuitEvent;
import fr.dush.slalomgenerator.views.IView;
import fr.dush.slalomgenerator.views.pages.dialog.AboutDialog;

/**
 * Controller of generic commands : Quit, About, ...
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@Named
public class GenericController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenericController.class);

	@Inject
	private IView view;

	@Inject
	private ResourceBundle bundle;

	@Inject
	private EventBus bus;

	@Subscribe
	public void quit(final QuitEvent event) {
		//@formatter:off
		final String[] options = {
				bundle.getString("dialog.yes"),
				bundle.getString("dialog.no"),
				bundle.getString("dialog.cancel")
			};

		final int response = JOptionPane.showOptionDialog(null,
				bundle.getString("dialog.quitconfirm.message"),
				bundle.getString("dialog.quitconfirm.title"),
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[2]);
		//@formatter:on

		switch (response) {
			case 0:
				// YES : save before quit
				bus.post(new SaveConfigurationEvent(event));
				break;
			case 1:
				// NO Quit
				view.quit();
				break;
			case 2:
				// Do nothing
				break;
			default:
				LOGGER.warn("Quit message not in range 0-2 : {}", response);
		}
	}

	@Subscribe
	public void displayAbout(AboutEvent event) {
		new AboutDialog(bundle, null).setVisible(true);
	}

	@Subscribe
	public void displayError(ExceptionEvent event) {
		JOptionPane.showMessageDialog(null, event.getThrowable().getClass().getSimpleName() + " : " + event.getThrowable().getMessage(),
				bundle.getString("dialog.error.title"), JOptionPane.ERROR_MESSAGE);
	}
}
