package fr.dush.slalomgenerator.views.pages;

import static com.google.common.collect.Lists.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.util.ClassUtils;

import com.google.common.base.Strings;
import com.google.common.eventbus.EventBus;

import fr.dush.slalomgenerator.dto.enums.DIRECTION;
import fr.dush.slalomgenerator.dto.enums.DIRECTION_CHANGE;
import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.dto.model.GeneratorParameter;
import fr.dush.slalomgenerator.dto.model.Sequence;
import fr.dush.slalomgenerator.events.generic.QuitEvent;
import fr.dush.slalomgenerator.events.model.ModelEvent;
import fr.dush.slalomgenerator.events.model.dialog.CreateRequestEvent;
import fr.dush.slalomgenerator.views.model.renderer.ActionRenderer;
import fr.dush.slalomgenerator.views.model.renderer.ActionRenderer.Action;
import fr.dush.slalomgenerator.views.model.renderer.AlignRenderer;
import fr.dush.slalomgenerator.views.model.renderer.BooleanRenderer;
import fr.dush.slalomgenerator.views.model.renderer.DirectionChangeRenderer;
import fr.dush.slalomgenerator.views.model.renderer.DirectionRenderer;
import fr.dush.slalomgenerator.views.utils.EventUtils;
import fr.dush.slalomgenerator.views.utils.UiUtils;

@Named
@Scope("prototype")
@SuppressWarnings("serial")
public class HomePage extends JFrame {

	private static final Logger LOGGER = LoggerFactory.getLogger(HomePage.class);

	private static final int DEF_HEIGHT = 768;

	private static final int DEF_WIDTH = 1024;

	@Inject
	private ApplicationContext applicationContext;

	/** Internationalized strings */
	private ResourceBundle bundle;

	@Inject
	public HomePage(ResourceBundle bundle, final EventBus bus) throws HeadlessException {
		super(bundle.getString("home.title"));
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		this.bundle = bundle;
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				bus.post(new QuitEvent(this));
			}

		});
	}

	@PostConstruct
	public void build() {
		LOGGER.debug("build...");

		// Size and position
		setSize(new Dimension(DEF_WIDTH, DEF_HEIGHT));
		setLocationRelativeTo(null); // Center page

		// Menu bar
		setJMenuBar(new DefaultMenuBar(bundle));

		// Layout
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		getContentPane().add(panel, BorderLayout.CENTER);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 0.5;
		c.weighty = 0.5;
		c.insets = new Insets(10, 10, 10, 10);

		// Generators
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		panel.add(generatePanel(GeneratorParameter.class, false), c);

		// Sequence
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		panel.add(generatePanel(Sequence.class, true), c);

		// Tables
		c.gridx = 1;
		c.gridy = 0;
		c.gridheight = 2;

		panel.add(generatePanel(Figure.class, false), c);
	}

	/**
	 * Generate vertical panel containing Label, Table and Buttons.
	 *
	 * @param clazz
	 * @param readOnly
	 * @return
	 */
	private JPanel generatePanel(Class<?> clazz, boolean readOnly) {
		// Find model for this class ...
		final String className = clazz.getSimpleName();
		final TableModel tableModel = applicationContext.getBean("model" + className, TableModel.class);

		// Create JTable (in JScrollPane)
		final JTable table = new JTable(tableModel);
		addRenderer(table, clazz, readOnly);

		final JScrollPane scrollTable = new JScrollPane(table);
		scrollTable.add(Box.createHorizontalGlue());
		scrollTable.add(Box.createVerticalGlue());

		// Buttons panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 0));

		if (!readOnly) {
			buttonPanel.add(generateButton("button.add", "edit_add-16.png", CreateRequestEvent.class, clazz));
		}

		JPanel sequencePanel = new JPanel();
		sequencePanel.setLayout(new BoxLayout(sequencePanel, BoxLayout.PAGE_AXIS));
		sequencePanel.setBorder(BorderFactory.createTitledBorder(bundle.getString("layout." + className.toLowerCase())));

		sequencePanel.add(scrollTable);
		sequencePanel.add(buttonPanel);

		return sequencePanel;
	}

	private void addRenderer(final JTable table, Class<?> clazz, boolean readOnly) {
		table.setDefaultRenderer(Boolean.class, new BooleanRenderer());
		table.setDefaultRenderer(DIRECTION.class, new DirectionRenderer(bundle));
		table.setDefaultRenderer(DIRECTION_CHANGE.class, new DirectionChangeRenderer(bundle));
		table.setDefaultRenderer(clazz, generateActionRenderer(readOnly, clazz));
		table.setDefaultEditor(clazz, generateActionRenderer(readOnly, clazz));

		// Spécial values
		for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
			final Class<?> columnClass = table.getColumnClass(i);

			try {
				if (ClassUtils.forName("int", getClass().getClassLoader()).equals(columnClass)) {
					// Center align integer...
					final TableColumn column = table.getColumnModel().getColumn(i);
					column.setCellRenderer(new AlignRenderer(SwingConstants.CENTER));

				} else if (String.class.equals(columnClass)) {
					// Set width to Strings
					final TableColumn column = table.getColumnModel().getColumn(i);
					column.setPreferredWidth(250);
				}
			} catch (Exception e) {
				LOGGER.warn("Error while setting renderer...", e);
			}
		}
	}

	private ActionRenderer generateActionRenderer(boolean readOnly, Class<?> clazz) {
		List<Action> actions = newArrayList(Action.DELETE);

		// Edit or show ?
		actions.add(readOnly ? Action.SHOW : Action.EDIT);

		// If generator parameter table : add generate action
		if(GeneratorParameter.class.isAssignableFrom(clazz)) {
			actions.add(Action.GENERATE);
		}

		return new ActionRenderer(bundle, actions.toArray(new Action[actions.size()]));
	}

	/**
	 * Create simple button, with or withount icon.
	 *
	 * @param name
	 * @param icon
	 * @param eventClass TODO
	 * @return
	 */
	private <ModelType> JButton generateButton(String name, String icon, Class<? extends ModelEvent> eventClass, Class<ModelType> clazz) {
		final JButton button = new JButton(bundle.getString(name));
		if (!Strings.isNullOrEmpty(icon)) button.setIcon(UiUtils.getIcon(icon));

		EventUtils.connect(button, eventClass, clazz);

		return button;
	}
}
