package fr.dush.slalomgenerator.views.utils;

import javax.swing.ImageIcon;

/**
 * Static class providing utilities methods.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
public class UiUtils {

	/**
	 * Get icon by name in classpath.
	 *
	 * @param iconName Icon name (with extension)
	 * @return
	 */
	public static ImageIcon getIcon(String iconName) {
		return new ImageIcon(UiUtils.class.getResource("/icons/" + iconName));
	}
}
