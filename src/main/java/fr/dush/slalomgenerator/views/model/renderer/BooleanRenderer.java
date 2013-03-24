package fr.dush.slalomgenerator.views.model.renderer;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dush.slalomgenerator.views.utils.UiUtils;

/**
 * Display boolean with icons.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class BooleanRenderer extends DefaultTableCellRenderer {

	private static final Logger LOGGER = LoggerFactory.getLogger(BooleanRenderer.class);

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		setText(null);
		setHorizontalAlignment(SwingConstants.CENTER);

		if (value instanceof Boolean) {
			setIcon(UiUtils.getIcon((Boolean) value ? "agt_action_success-16.png" : "agt_stop-16.png"));

		} else {
			LOGGER.warn("{} is not boolean.", value);
			setText(value.toString());
		}

		return this;
	}

}
