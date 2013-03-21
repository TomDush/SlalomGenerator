package fr.dush.slalomgenerator.views.pages;

import java.awt.HeadlessException;
import java.awt.event.InputEvent;
import java.util.ResourceBundle;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.google.common.base.Strings;

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

		// TODO Menu Shortcuts...

		// File menu
		JMenu file = new JMenu(bundle.getString("menu.file"));
		file.add(generateJMenuItem("menu.new", "filenew-16.png", KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK)));
		file.addSeparator();
		file.add(generateJMenuItem("menu.load", "fileopen-16.png", KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK)));
		file.add(generateJMenuItem("menu.save", "filesave-16.png", KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK)));
		file.addSeparator();
		file.add(generateJMenuItem("menu.quit", "stop-16.png", KeyStroke.getKeyStroke('Q', InputEvent.CTRL_DOWN_MASK)));

		this.add(file);

		// Slalom menu
		JMenu slalomMenu = new JMenu(bundle.getString("menu.slalom"));
		slalomMenu.add(generateJMenuItem("menu.figure.create", "edit_add-16.png", null));
		slalomMenu.add(generateJMenuItem("menu.generator.create", "agt_softwareD-16.png", null));

		this.add(slalomMenu);

		// About menu
		JMenu help = new JMenu(bundle.getString("menu.help"));
		help.add(generateJMenuItem("menu.about", "info-16.png", null));
		this.add(help);
	}

	private JMenuItem generateJMenuItem(String labelCode, String iconName, KeyStroke keyStroke) {
		final JMenuItem item = new JMenuItem(bundle.getString(labelCode));
		if (!Strings.isNullOrEmpty(iconName)) {
			item.setIcon(UiUtils.getIcon(iconName));
		}
		if (null != keyStroke) item.setAccelerator(keyStroke);

		return item;
	}
}