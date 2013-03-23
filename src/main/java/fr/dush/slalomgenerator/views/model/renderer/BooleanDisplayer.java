package fr.dush.slalomgenerator.views.model.renderer;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dush.slalomgenerator.views.utils.UiUtils;

@SuppressWarnings("serial")
public class BooleanDisplayer extends DefaultTableCellRenderer {

	private static final Logger LOGGER = LoggerFactory.getLogger(BooleanDisplayer.class);

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value instanceof Boolean) {
			final JLabel label = new JLabel(UiUtils.getIcon((Boolean) value ? "agt_action_success-24.png" : "agt_stop-24.png"));
			label.setHorizontalAlignment(SwingConstants.CENTER);

			return label;
		}

		LOGGER.warn("{} is not boolean.", value);
		return new JLabel(value.toString());
	}

}
