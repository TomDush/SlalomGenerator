package fr.dush.slalomgenerator.views.pages.dialog;

import java.awt.Container;
import java.awt.Dimension;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

/**
 * Dialog to display generic informations on this application.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class FigureDialog<T> extends JDialog {

	/** Figure to display */
	private final T object;

	/** SpringLayout : beautiful layout ! */
	private SpringLayout layout;

	/** Dialog head message */
	private JLabel title;

	/** When diplay lines, this is last label */
	private Container topComponent;

	/**
	 * Construct dialog to display parameters object
	 *
	 * @param bundle
	 * @param frame
	 * @param object
	 * @param properties
	 */
	public FigureDialog(ResourceBundle bundle, JFrame frame, T object, List<String> properties) {
		super(frame, true);
		layout = new SpringLayout();
		setLayout(layout);

		// ** Object
		if (null == object) throw new IllegalArgumentException("object mustn't not be null.");
		this.object = object;

		// Bundle prefix
		String prefix = "dialog." + object.getClass().getSimpleName().toLowerCase();

		// ** Title & Header
		setTitle(bundle.getString(prefix + ".title"));

		// Page title
		title = new JLabel(bundle.getString(prefix + ".header"));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(title);
		layout.putConstraint(SpringLayout.WEST, title, 5, SpringLayout.WEST, getContentPane());
		layout.putConstraint(SpringLayout.EAST, title, -5, SpringLayout.EAST, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, title, 5, SpringLayout.NORTH, getContentPane());

		// Lines
		for (String prop : properties) {
			final String label = bundle.getString(prefix + "." + prop.toLowerCase());
			final JComponent field = new JTextField();
			addLine(label, field);
		}

//		addLine("Label 1 :", new JTextField("Text field", 15));
//		addLine("Label 2 :", new JTextField("Text field", 15));
//		addLine("Label 3 :", new JTextField("Text field", 15));
//		addLine("Label 4 :", new JTextField("Text field", 15));

		// setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// pack();
		setSize(300, 400);

		this.setLocationRelativeTo(null);
	}

	private void addLine(String labelString, JComponent component) {

		// Label
		final JLabel label = new JLabel(labelString);
		label.setMinimumSize(new Dimension(200, 20));
		getContentPane().add(label);

		layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, getContentPane());
		if (null != topComponent) {
			layout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.SOUTH, topComponent);
			layout.putConstraint(SpringLayout.EAST, label, 0, SpringLayout.EAST, topComponent);
		} else {
			layout.putConstraint(SpringLayout.NORTH, label, 10, SpringLayout.SOUTH, title);
		}

		// Field
		getContentPane().add(component);

		layout.putConstraint(SpringLayout.EAST, component, -5, SpringLayout.EAST, getContentPane());
		layout.putConstraint(SpringLayout.WEST, component, 5, SpringLayout.EAST, label);
		if (null != topComponent) {
			layout.putConstraint(SpringLayout.NORTH, component, 5, SpringLayout.SOUTH, topComponent);
		} else {
			layout.putConstraint(SpringLayout.NORTH, component, 10, SpringLayout.SOUTH, title);

		}

		// Change top
		topComponent = label;
	}
}
