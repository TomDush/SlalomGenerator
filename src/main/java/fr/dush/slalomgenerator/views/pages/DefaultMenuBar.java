package fr.dush.slalomgenerator.views.pages;

import java.awt.HeadlessException;
import java.awt.event.InputEvent;
import java.util.ResourceBundle;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.google.common.base.Strings;

import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.dto.model.GeneratorParameter;
import fr.dush.slalomgenerator.events.UIEvent;
import fr.dush.slalomgenerator.events.configuration.ClearConfigurationEvent;
import fr.dush.slalomgenerator.events.configuration.LoadConfigurationEvent;
import fr.dush.slalomgenerator.events.configuration.SaveConfigurationEvent;
import fr.dush.slalomgenerator.events.generic.AboutEvent;
import fr.dush.slalomgenerator.events.generic.QuitEvent;
import fr.dush.slalomgenerator.events.model.CreateRequestEvent;
import fr.dush.slalomgenerator.events.model.ModelEvent;
import fr.dush.slalomgenerator.views.utils.EventUtils;
import fr.dush.slalomgenerator.views.utils.UiUtils;

/**
 * Main window menu bar.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class DefaultMenuBar extends JMenuBar {
	private ResourceBundle bundle;

	public DefaultMenuBar(ResourceBundle bundle) throws HeadlessException {
		super();
		this.bundle = bundle;

		// File menu
		JMenu file = new JMenu(bundle.getString("menu.file"));
		file.add(generateJMenuItem("menu.new", "filenew-16.png", KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK), ClearConfigurationEvent.class));
		file.addSeparator();
		file.add(generateJMenuItem("menu.load", "fileopen-16.png", KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK), LoadConfigurationEvent.class));
		file.add(generateJMenuItem("menu.save", "filesave-16.png", KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK), SaveConfigurationEvent.class));
		file.addSeparator();
		file.add(generateJMenuItem("menu.quit", "stop-16.png", KeyStroke.getKeyStroke('Q', InputEvent.CTRL_DOWN_MASK), QuitEvent.class));

		this.add(file);

		// Slalom menu
		JMenu slalomMenu = new JMenu(bundle.getString("menu.slalom"));
		slalomMenu.add(generateJMenuItem("menu.figure.create", "edit_add-16.png", null, CreateRequestEvent.class, Figure.class));
		slalomMenu.add(generateJMenuItem("menu.generator.create", "agt_softwareD-16.png", null, CreateRequestEvent.class, GeneratorParameter.class));

		this.add(slalomMenu);

		// About menu
		JMenu help = new JMenu(bundle.getString("menu.help"));
		help.add(generateJMenuItem("menu.about", "info-16.png", null, AboutEvent.class));
		this.add(help);
	}

	private JMenuItem generateJMenuItem(String labelCode, String iconName, KeyStroke keyStroke, Class<? extends UIEvent> eventClass) {
		final JMenuItem item = new JMenuItem(bundle.getString(labelCode));
		if (!Strings.isNullOrEmpty(iconName)) {
			item.setIcon(UiUtils.getIcon(iconName));
		}
		if (null != keyStroke) item.setAccelerator(keyStroke);

		// Event
		if (null != eventClass) EventUtils.connect(item, eventClass);

		return item;
	}

	private JMenuItem generateJMenuItem(String labelCode, String iconName, KeyStroke keyStroke, Class<? extends ModelEvent> eventClass, Class<?> modelType) {
		final JMenuItem item = generateJMenuItem(labelCode, iconName, keyStroke, null);

		if (null != eventClass && null != modelType) EventUtils.connect(item, eventClass, modelType);

		return item;
	}
}