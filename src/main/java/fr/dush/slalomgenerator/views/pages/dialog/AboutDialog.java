package fr.dush.slalomgenerator.views.pages.dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Dialog to display generic informations on this application.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class AboutDialog extends JDialog {

	public AboutDialog(ResourceBundle bundle, JFrame frame) {
		super(frame, bundle.getString("dialog.about.title"), true);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		// Html pane
		JEditorPane html = new JEditorPane("text/html", "<h3>" + bundle.getString("home.title") + "</h3>" + "<p>" + bundle.getString("dialog.about.message")
				+ "</p> <h3>" + bundle.getString("dialog.about.author") + "</h3>" + bundle.getString("dialog.about.authors"));
		html.setEditable(false);

		getContentPane().add(html);

		// Close button
		JButton close = new JButton(bundle.getString("dialog.close"));
		close.setHorizontalAlignment(SwingConstants.CENTER);
		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				dispose();
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

		buttonPanel.add(close);

		getContentPane().add(buttonPanel);

		// Generic configuration
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();

		this.setLocationRelativeTo(null);
	}
}