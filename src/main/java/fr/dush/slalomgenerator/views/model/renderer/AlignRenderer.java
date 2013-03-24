package fr.dush.slalomgenerator.views.model.renderer;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

@SuppressWarnings("serial")
public class AlignRenderer extends DefaultTableCellRenderer {

	/** Expected horizontal alignment */
	private int alignment;

	/**
	 * Set expected horizontal alignment
	 *
	 * @param alignment Constant defined in {@link SwingConstants}.
	 */
	public AlignRenderer(int alignment) {
		this.alignment = alignment;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		setHorizontalAlignment(alignment);

		return this;
	}
}
