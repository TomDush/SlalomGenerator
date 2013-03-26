package fr.dush.slalomgenerator.views.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import fr.dush.slalomgenerator.dao.model.ISaveDAO;
import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.dto.model.GeneratorParameter;
import fr.dush.slalomgenerator.dto.model.Save;
import fr.dush.slalomgenerator.dto.model.Sequence;
import fr.dush.slalomgenerator.events.configuration.ClearConfigurationEvent;
import fr.dush.slalomgenerator.events.configuration.LoadConfigurationEvent;
import fr.dush.slalomgenerator.events.configuration.SaveConfigurationEvent;
import fr.dush.slalomgenerator.events.generic.FunctionalError;
import fr.dush.slalomgenerator.events.generic.QuitEvent;
import fr.dush.slalomgenerator.events.model.FullChangeModel;

/**
 * This controller manage save or load file, and clear events.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@Named
public class ConfigurationSaveController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationSaveController.class);

	@Inject
	private EventBus bus;

	@Inject
	private ISaveDAO saveDAO;

	@Inject
	private ResourceBundle bundle;

	private File defaultFile = new File(".");

	@Subscribe
	public void saveConfiguration(SaveConfigurationEvent event) {
		// Choosing file
		final File selectedFile = chooseFile(false, "saver");
		if (null == selectedFile) return;

		// If file exist, ask user confirmation. If not, cancel event
		if (selectedFile.exists()) {

			final int confirm = JOptionPane.showConfirmDialog(null, bundle.getString("dialog.saver.alreadyexist.message"));
			if (JOptionPane.OK_OPTION != confirm) return;
		}

		try {
			// File OK !
			saveDAO.saveIntoFile(selectedFile);

			// If event source is close event, confirm event and resend it
			if (event.getSource() instanceof QuitEvent) {
				((QuitEvent) event.getSource()).setConfirmed(true);
				bus.post(event.getSource());
			}

		} catch (IOException e) {
			LOGGER.warn("Can't save into file {}...", selectedFile, e);
			bus.post(new FunctionalError(event, bundle.getString("dialog.saver.ioexception") + "(" + e.getMessage() + ")"));
		}
	}

	@Subscribe
	public void clearConfiguration(ClearConfigurationEvent event) {
		// Clear parameters
		saveDAO.clear();

		// Send events to refresh views
		bus.post(new FullChangeModel<Figure>(Figure.class, new ArrayList<Figure>()));
		bus.post(new FullChangeModel<GeneratorParameter>(GeneratorParameter.class, new ArrayList<GeneratorParameter>()));
		bus.post(new FullChangeModel<Sequence>(Sequence.class, new ArrayList<Sequence>()));
	}

	@Subscribe
	public void loadConfiguration(LoadConfigurationEvent event) throws IOException {
		LOGGER.info("Received : {} from {}", event, event.getSource());

		// Choosing file
		final File selectedFile = chooseFile(true, "loader");

		// If file OK, load it
		if (null != selectedFile && selectedFile.exists()) {

			// Clear parameters
			saveDAO.clear();

			// load new ones
			final Save save = saveDAO.loadFile(selectedFile);

			// Send events to refresh views
			bus.post(new FullChangeModel<Figure>(Figure.class, save.getFigures()));
			bus.post(new FullChangeModel<GeneratorParameter>(GeneratorParameter.class, save.getGeneratorParameters()));
			bus.post(new FullChangeModel<Sequence>(Sequence.class, save.getSequences()));

		}
	}

	/**
	 * Ask to user to choose file.
	 *
	 * @param exists Choosed file must exist. If FALSE, existing and not existing files are valid.
	 * @param bundleName Properties name
	 * @return NULL if file invalid or action is canceled.
	 */
	private File chooseFile(boolean exists, final String bundleName) {
		// Lets choose file
		JFileChooser chooser = new JFileChooser(defaultFile);
		chooser.setApproveButtonText(bundle.getString("dialog." + bundleName + ".approve"));
		chooser.setDialogTitle(bundle.getString("dialog." + bundleName + ".title"));

		// File must exist
//		if (exists) { // FIXME Doesn't work...
//			chooser.setAcceptAllFileFilterUsed(false);
//			chooser.addChoosableFileFilter(new FileFilter() {
//
//				@Override
//				public String getDescription() {
//					return bundle.getString("dialog.file.exist");
//				}
//
//				@Override
//				public boolean accept(File f) {
//					return f.exists();
//				}
//			});
//		}

		final int response = chooser.showOpenDialog(null);
		if (JFileChooser.APPROVE_OPTION != response) return null;

		// Get selected file, and test it
		final File selectedFile = chooser.getSelectedFile();
		if (null == selectedFile) {
			bus.post(new FunctionalError(chooser, bundle.getString("dialog.saver.nullfile")));
		}

		defaultFile = selectedFile.getParentFile();

		return selectedFile;
	}

}
