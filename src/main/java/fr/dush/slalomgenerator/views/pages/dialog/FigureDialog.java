package fr.dush.slalomgenerator.views.pages.dialog;

import java.util.ResourceBundle;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import fr.dush.slalomgenerator.dto.model.Figure;

/**
 * Dialog to display generic informations on this application.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class FigureDialog extends JDialog {

	/** Figure to display */
	private final Figure figure;

	public FigureDialog(ResourceBundle bundle, JFrame frame, Figure figure) {
		super(frame, bundle.getString("dialog.figure.title"), true);

		this.figure = figure; // TODO check is not null

		// Layout
		final SpringLayout layout = new SpringLayout();
		setLayout(layout);

		//

        final JLabel label = new JLabel("Label: ");
		getContentPane().add(label);
        final JTextField nameField = new JTextField("Text field", 15);
		getContentPane().add(nameField);
		layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, getContentPane());

		layout.putConstraint(SpringLayout.WEST, nameField, 5, SpringLayout.EAST, label);
		layout.putConstraint(SpringLayout.EAST, nameField, 5, SpringLayout.EAST, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, nameField, 5, SpringLayout.NORTH, getContentPane());

		// Generic configuration
//		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		pack();

		this.setLocationRelativeTo(null);
	}
}
