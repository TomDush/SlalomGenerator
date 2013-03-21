package fr.dush.slalomgenerator.views.pages;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.util.ResourceBundle;

import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;

import org.springframework.context.annotation.Scope;

@Named
@Scope("prototype")
@SuppressWarnings("serial")
public class HomePage extends JFrame {

	private static final int DEF_HEIGHT = 768;

	private static final int DEF_WIDTH = 1024;

	/** Internationalized strings */
	private ResourceBundle bundle;

	/** Generic layout */
	private BorderLayout layout;

	@Inject
	public HomePage(ResourceBundle bundle) throws HeadlessException {
		super(bundle.getString("home.title"));

		this.bundle = bundle;

		build();
	}

	private void build() {
		// Size and position
		setSize(new Dimension(DEF_WIDTH, DEF_HEIGHT));
		setLocationRelativeTo(null); // Center page

		// Menu bar
		setJMenuBar(new DefaultMenuBar(bundle));

		// Layout
		layout = new BorderLayout();
		setLayout(layout);

		add(new JTable(), BorderLayout.CENTER);
		add(new JButton("Hello World !"), BorderLayout.WEST);
	}

}
