package fr.dush.slalomgenerator.views.model.renderer;

import java.awt.Component;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dush.slalomgenerator.dto.enums.DIRECTION_CHANGE;
import fr.dush.slalomgenerator.views.utils.UiUtils;

@SuppressWarnings("serial")
public class DirectionChangeDisplayer extends DefaultTableCellRenderer {

	private static final Logger LOGGER = LoggerFactory.getLogger(DirectionChangeDisplayer.class);

	/** Internationalized strings */
	private ResourceBundle bundle;

	public DirectionChangeDisplayer(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		final JLabel label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);

		// Icone
		if (value instanceof DIRECTION_CHANGE) {
			label.setToolTipText(bundle.getString("table.directionchange." + value.toString().toLowerCase()));

			switch ((DIRECTION_CHANGE) value) {
				case KEEP:
					label.setIcon(UiUtils.getIcon("agt_stop-24.png"));
					break;

				case CHANGE:
					label.setIcon(UiUtils.getIcon("agt_action_success-24.png"));
					break;

				default:
					label.setIcon(UiUtils.getIcon("question-mark-24.png"));
			}

			return label;
		}

		LOGGER.warn("{} is not boolean.", value);
		return new JLabel(value.toString());
	}

}
