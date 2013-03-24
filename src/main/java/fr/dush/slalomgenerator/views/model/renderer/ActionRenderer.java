package fr.dush.slalomgenerator.views.model.renderer;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.dush.slalomgenerator.views.utils.UiUtils;

/**
 * Column renderer which display action icons : edit and delete.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class ActionRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActionRenderer.class);

	/** Internationalized strings */
	private ResourceBundle bundle;

	/** Panel display in Cell */
	private JPanel panel;

	/** Object displayed */
	private Object value;

	protected ActionRenderer() {
		final FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
		flowLayout.setVgap(0);
		flowLayout.setHgap(0);
		panel = new JPanel(flowLayout);

		panel.setBorder(null);
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	public ActionRenderer(ResourceBundle bundle, ACTION... actions) {
		this();
		this.bundle = bundle;

		// ** Adding actions
		for (ACTION action : actions) {
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

	private JButton generateIcon(final ACTION action) {

			final String name = action.toString().toLowerCase();

		JButton button = new JButton(UiUtils.getIcon(bundle.getString("table.actions.icon." + name)));
		button.setToolTipText(bundle.getString("table.actions.tooltip." + name));

		// Configure button
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 2));

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Testing events ...
				LOGGER.info("Click on " + action + " for " + value);
			}
		});

		return button;
	}

	@Override
	public Object getCellEditorValue() {
		return null;
	}

	public enum ACTION {

		/** Delete item action */
		DELETE,

		/** Edit item action */
		EDIT,

		/** Show item action */
		SHOW;
	}
}
