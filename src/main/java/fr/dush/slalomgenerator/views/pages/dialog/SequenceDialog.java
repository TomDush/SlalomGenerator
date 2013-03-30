package fr.dush.slalomgenerator.views.pages.dialog;

import static com.google.common.collect.Lists.*;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import com.google.common.eventbus.EventBus;

import fr.dush.slalomgenerator.dto.enums.DIRECTION;
import fr.dush.slalomgenerator.dto.enums.DIRECTION_CHANGE;
import fr.dush.slalomgenerator.dto.model.Figure;
import fr.dush.slalomgenerator.dto.model.Sequence;
import fr.dush.slalomgenerator.events.model.ModelObjectEvent;
import fr.dush.slalomgenerator.events.model.confirmed.ConfirmEditEvent;
import fr.dush.slalomgenerator.events.model.confirmed.ConfirmNewEvent;
import fr.dush.slalomgenerator.views.model.TableModelReflector;
import fr.dush.slalomgenerator.views.model.renderer.AlignRenderer;
import fr.dush.slalomgenerator.views.model.renderer.BooleanRenderer;
import fr.dush.slalomgenerator.views.model.renderer.DirectionChangeRenderer;
import fr.dush.slalomgenerator.views.model.renderer.DirectionRenderer;

/**
 * Display sequence
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class SequenceDialog extends JFrame implements ActionListener {

	/** Application bus event, to fire sequence save event */
	private EventBus bus;

	/** Localization resources */
	private ResourceBundle bundle;

	/** Sequence displayed */
	private Sequence sequence;

	/** True if sequence was just generated */
	private boolean newSequence = false;

	/** Sequence name field */
	private final JTextField titleField;

	public SequenceDialog(ResourceBundle bundle, EventBus bus, Sequence sequence, boolean newSequence) throws HeadlessException {
		super(bundle.getString("dialog.sequence.title"));

		// Attributes
		this.bundle = bundle;
		this.bus = bus;
		this.sequence = sequence;
		this.newSequence = newSequence;

		// Close handler
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				closeFrame();
			}
		});

		// Layout and frame properties
		setLayout(new BorderLayout());
		setSize(500, 600);
		setLocationRelativeTo(null);

		// Title form
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
		add(titlePanel, BorderLayout.PAGE_START);
		titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		titlePanel.add(new JLabel(bundle.getString("dialog.sequence.name") + " : "));
		titleField = new JTextField(sequence.getName());
		titlePanel.add(titleField);

		// Figure table
		buildTableAndAdd(bundle, sequence);

		// Buttons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.LINE_AXIS));
		add(buttonsPanel, BorderLayout.PAGE_END);
		buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		buttonsPanel.add(separator(50, 1));
		buttonsPanel.add(addCloseEvent(new JButton(bundle.getString("dialog.close"))));
		buttonsPanel.add(separator(10, 1));
		buttonsPanel.add(addSaveEvent(new JButton(bundle.getString("dialog.save"))));
		buttonsPanel.add(separator(50, 1));
		titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	/**
	 * Save button clicked.
	 *
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		sequence.setName(titleField.getText());

		// this frame will be closed by dao controller : when sequence is saved.
		final ModelObjectEvent event = newSequence ? new ConfirmNewEvent(this, sequence) : new ConfirmEditEvent(this, sequence);
		bus.post(event);

	}

	private Component addSaveEvent(JButton jButton) {
		jButton.addActionListener(this);
		return jButton;
	}

	private Component addCloseEvent(JButton jButton) {
		jButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				closeFrame();
			}
		});
		return jButton;
	}

	/**
	 * Shame on me... :(
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	private JPanel separator(int x, int y) {
		final JPanel jPanel = new JPanel();
		jPanel.setSize(x, y);

		return jPanel;
	}

	/**
	 * Build table and add it on frame center
	 *
	 * @param bundle
	 * @param sequence
	 */
	private void buildTableAndAdd(ResourceBundle bundle, Sequence sequence) {
		// Figure model does NOT listen events !!
		final TableModelReflector<Figure> figureModel = new TableModelReflector<Figure>(bundle, Figure.class, newArrayList("name", "description", "plotSize",
				"startingDirection", "inverseDirection", "aboutTurn"), sequence.getFigures());

		// ** Configure table model (set boolean properties...)
		figureModel.setBooleanProperties(newArrayList("aboutTurn"));
		final JTable table = new JTable(figureModel);

		// ** Customize display
		table.setDefaultRenderer(Boolean.class, new BooleanRenderer());
		table.setDefaultRenderer(DIRECTION.class, new DirectionRenderer(bundle));
		table.setDefaultRenderer(DIRECTION_CHANGE.class, new DirectionChangeRenderer(bundle));

		// Integers display
		table.getColumnModel().getColumn(2).setCellRenderer(new AlignRenderer(SwingConstants.CENTER));

		// Name and description display
		table.getColumnModel().getColumn(0).setPreferredWidth(250);
		table.getColumnModel().getColumn(1).setPreferredWidth(250);

		// ** Adding to frame
		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	/**
	 * Close frame, ask if it's new sequence.
	 */
	private void closeFrame() {
		if(newSequence) {
			final int askResponse = JOptionPane.showConfirmDialog(this, bundle.getString("dialog.sequence.saveordiscard"));
			if (JOptionPane.OK_OPTION == askResponse) {
				actionPerformed(null); // save
			} else if(JOptionPane.CANCEL_OPTION == askResponse) {
				return;
			}
		}

		dispose();
	}

}
