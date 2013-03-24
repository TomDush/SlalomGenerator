package fr.dush.slalomgenerator.views.model.renderer;

import java.awt.Component;
import java.util.ResourceBundle;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dush.slalomgenerator.dto.enums.DIRECTION;
import fr.dush.slalomgenerator.views.utils.UiUtils;

/**
 * Display {@link DIRECTION} with icons.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class DirectionRenderer extends DefaultTableCellRenderer {

	private static final Logger LOGGER = LoggerFactory.getLogger(DirectionRenderer.class);

	/** Internationalized strings */
	private ResourceBundle bundle;

	public DirectionRenderer(ResourceBundle bundle) {
		this.bundle = bundle;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		setText(null);
		setHorizontalAlignment(SwingConstants.CENTER);

		// Icone
		if (value instanceof DIRECTION) {
			setToolTipText(bundle.getString("table.direction." + value.toString().toLowerCase()));

			switch ((DIRECTION) value) {
				case BACKWARD:
					setIcon(UiUtils.getIcon("1leftarrow-16.png"));
					break;

				case FORWARD:
					setIcon(UiUtils.getIcon("1rightarrow-16.png"));
					break;

				default:
					setIcon(UiUtils.getIcon("question-mark-16.png"));
			}

		} else  {
			LOGGER.warn("{} is not boolean.", value);
			setText(value.toString());
		}

		return this;
	}

}
