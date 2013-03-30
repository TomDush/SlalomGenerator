package fr.dush.slalomgenerator.views.model.renderer;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.util.ResourceBundle;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import lombok.Getter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dush.slalomgenerator.events.model.GenerateEvent;
import fr.dush.slalomgenerator.events.model.ModelObjectEvent;
import fr.dush.slalomgenerator.events.model.dialog.DeleteRequestEvent;
import fr.dush.slalomgenerator.events.model.dialog.EditRequestEvent;
import fr.dush.slalomgenerator.views.utils.EventUtils;
import fr.dush.slalomgenerator.views.utils.IValueProvider;
import fr.dush.slalomgenerator.views.utils.UiUtils;

/**
 * Column renderer which display action icons : edit and delete.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class ActionRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer, IValueProvider {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActionRenderer.class);

	/** Internationalized strings */
	private ResourceBundle bundle;

	/** Panel display in Cell */
	private JPanel panel;

	/** Object displayed */
	@Getter
	private Object value;

	protected ActionRenderer() {
		final FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		panel = new JPanel(flowLayout);

		panel.setBorder(null);
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	public ActionRenderer(ResourceBundle bundle, Action... actions) {
		this();
		this.bundle = bundle;

		// ** Adding actions
		for (Action action : actions) {
			panel.add(generateIcon(action));
		}
	}

	/**
	 * Update object on which method will be executed.
	 *
	 * @param object
	 * @param isSelected
	 * @param table
	 */
	protected void updateData(Object object, boolean isSelected, JTable table) {
		this.value = object;

		if (isSelected) {
			panel.setBackground(table.getSelectionBackground());
		} else {
			panel.setBackground(table.getBackground());
		}
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		updateData(value, isSelected, table);

		return panel;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		updateData(value, true, table);

		return panel;
	}

	private JButton generateIcon(final Action action) {

		final String name = action.toString().toLowerCase();

		JButton button = new JButton(UiUtils.getIcon(bundle.getString("table.actions.icon." + name)));
		button.setToolTipText(bundle.getString("table.actions.tooltip." + name));

		// Configure button
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));

		EventUtils.connectModelObjectEvent(button, getEventType(action), this);

		return button;
	}

	@Override
	public Object getCellEditorValue() {
		return null;
	}

	protected Class<? extends ModelObjectEvent> getEventType(Action action) {
		switch (action) {
			case DELETE:
				return DeleteRequestEvent.class;

			case EDIT:
			case SHOW:
				return EditRequestEvent.class;

			case GENERATE:
				return GenerateEvent.class;

			default:
				LOGGER.error("Not event type defined for " + action + " action.");
				return null;
		}
	}

	/**
	 * Iconifable actions
	 *
	 * @author Thomas Duchatelle (tomdush@gmail.com)
	 *
	 */
	public enum Action {

		/** Delete item action */
		DELETE,

		/** Edit item action */
		EDIT,

		/** Show item action */
		SHOW,

		/** Generate sequence */
		GENERATE;
	}
}
