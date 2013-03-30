package fr.dush.slalomgenerator.views.pages.dialog;

import static com.google.common.collect.Maps.*;
import static com.google.common.collect.Sets.*;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import lombok.Setter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.google.common.base.Strings;
import com.google.common.eventbus.EventBus;

import fr.dush.slalomgenerator.dto.enums.DIRECTION;
import fr.dush.slalomgenerator.dto.enums.DIRECTION_CHANGE;
import fr.dush.slalomgenerator.events.generic.ExceptionEvent;
import fr.dush.slalomgenerator.events.generic.FunctionalErrorEvent;
import fr.dush.slalomgenerator.events.model.confirmed.ConfirmEditEvent;
import fr.dush.slalomgenerator.events.model.confirmed.ConfirmNewEvent;
import fr.dush.slalomgenerator.exceptions.ViewException;

/**
 * Dialog to display generic informations on this application. Window is clsed by DaoControler, when saved is validated.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class FormDialog<T> extends JFrame implements ActionListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(FormDialog.class);

	/** Application event bus */
	private EventBus bus;

	/** Apllication localization resources */
	private ResourceBundle bundle;

	/** Object to display */
	private final T object;

	/** If it's new object (determine event type which will be posted) */
	@Setter
	private boolean newObject = false;

	/** SpringLayout : beautiful layout ! */
	private SpringLayout layout;

	/** Dialog head message */
	private JLabel title;

	/** When display lines, this is last label */
	private Container topComponent;

	/** Mapped field (to read) */
	private Map<String, JComponent> fields = newHashMap();

	/** Integer fields */
	private Set<String> integerFields = newHashSet();

	/**
	 * Construct dialog to display parameters object
	 *
	 * @param frame
	 * @param bundle
	 * @param bus
	 * @param object
	 * @param properties
	 * @throws LinkageError
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 */
	public FormDialog(JFrame frame, ResourceBundle bundle, EventBus bus, T object, List<String> properties) throws ViewException {
		super();
		layout = new SpringLayout();
		setLayout(layout);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(450, 40 + properties.size() * 30 + 50);

		// ** Object
		this.bus = bus;
		this.bundle = bundle;
		if (null == object) throw new IllegalArgumentException("object mustn't not be null.");
		this.object = object;

		// ** Build content
		try {
			build(bundle, object, properties);
		} catch (Exception e) {
			throw new ViewException(e.getMessage(), e);
		}

		// ** Dialog configuration
		// setModalityType(ModalityType.APPLICATION_MODAL);

		this.setLocationRelativeTo(null);
	}

	private void build(ResourceBundle bundle, T object, List<String> properties) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
			ClassNotFoundException, LinkageError {

		// Bundle prefix
		final String prefix = getBundlePrefix();

		// ** Title & Header
		setTitle(bundle.getString(prefix + "title"));

		// Page title
		title = new JLabel(bundle.getString(prefix + "header"));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(title);
		layout.putConstraint(SpringLayout.WEST, title, 5, SpringLayout.WEST, getContentPane());
		layout.putConstraint(SpringLayout.EAST, title, -5, SpringLayout.EAST, getContentPane());
		layout.putConstraint(SpringLayout.NORTH, title, 5, SpringLayout.NORTH, getContentPane());

		// Lines
		for (String prop : properties) {
			final String label = bundle.getString(prefix + prop.toLowerCase());
			final JComponent field = getField(object, prop);
			addLine(label, field);

			fields.put(prop, field);
		}

		// Buttons
		JButton cancelButton = new JButton(bundle.getString("dialog.cancel"));
		cancelButton.setHorizontalAlignment(SwingConstants.CENTER);
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Close dialog
				dispose();
			}
		});
		getContentPane().add(cancelButton);
		layout.putConstraint(SpringLayout.WEST, cancelButton, 20, SpringLayout.WEST, getContentPane());
		layout.putConstraint(SpringLayout.EAST, cancelButton, getWidth() / 2 - 10, SpringLayout.WEST, getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, cancelButton, -5, SpringLayout.SOUTH, getContentPane());

		JButton saveButton = new JButton(bundle.getString("dialog.save"));
		saveButton.setHorizontalAlignment(SwingConstants.CENTER);
		saveButton.addActionListener(this);
		getContentPane().add(saveButton);
		layout.putConstraint(SpringLayout.EAST, saveButton, -20, SpringLayout.EAST, getContentPane());
		layout.putConstraint(SpringLayout.WEST, saveButton, getWidth() / 2 + 10, SpringLayout.WEST, getContentPane());
		layout.putConstraint(SpringLayout.SOUTH, saveButton, -5, SpringLayout.SOUTH, getContentPane());
	}

	private String getBundlePrefix() {
		return "dialog." + this.object.getClass().getSimpleName().toLowerCase() + ".";
	}

	/**
	 * Called when save button pressed
	 */
	@Override
	public void actionPerformed(ActionEvent event) {
		LOGGER.info("Perform save button click !");
		try {
			// ** Collect values
			for (Entry<String, JComponent> entry : fields.entrySet()) {
				if (entry.getValue() instanceof JTextField && integerFields.contains(entry.getKey())) {
					try {
						final String newValue = ((JTextField) entry.getValue()).getText();
						object.getClass().getMethod("set" + StringUtils.capitalize(entry.getKey()), int.class).invoke(object, Integer.valueOf(newValue));

					} catch (NumberFormatException e) {
						bus.post(new FunctionalErrorEvent(this, bundle.getString(getBundlePrefix() + entry.getKey().toLowerCase()) + " "
								+ bundle.getString("error.notinteger")));
						return;
					}

				} else if (entry.getValue() instanceof JTextField) {
					final String newValue = ((JTextField) entry.getValue()).getText();
					// If name, it must be filled
					if(Strings.isNullOrEmpty(newValue) && "name".equals(entry.getKey())) {
						bus.post(new FunctionalErrorEvent(this, bundle.getString(getBundlePrefix() + entry.getKey().toLowerCase()) + " "
								+ bundle.getString("error.namenotfilled")));
						return;
					}
					object.getClass().getMethod("set" + StringUtils.capitalize(entry.getKey()), String.class).invoke(object, newValue);

				} else if (entry.getValue() instanceof JComboBox<?>) {
					final Object newValue = ((JComboBox<?>) entry.getValue()).getSelectedItem();
					object.getClass().getMethod("set" + StringUtils.capitalize(entry.getKey()), newValue.getClass()).invoke(object, newValue);

				} else if (entry.getValue() instanceof JCheckBox) {
					final boolean newValue = ((JCheckBox) entry.getValue()).isSelected();
					object.getClass().getMethod("set" + StringUtils.capitalize(entry.getKey()), boolean.class).invoke(object, newValue);
				}
			}

			// ** Prevent application
			LOGGER.debug("Modified object : {}", object);
			if (newObject) {
				bus.post(new ConfirmNewEvent(this, object));
			} else {
				bus.post(new ConfirmEditEvent(this, object));
			}

		} catch (Exception e) {
			LOGGER.error("Error while collecting new values.", e);
			bus.post(new ExceptionEvent(this, e));
		}

	}

	/**
	 * Get appropriate field type : JTextField, JComboBox, JCheckBox or J
	 *
	 * @param object
	 * @param prop
	 * @return
	 */
	private JComponent getField(T object, String prop) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			SecurityException, ClassNotFoundException, LinkageError {

		// ** Getter method
		final Method method = getMethod(object.getClass(), prop);

		// ** Get appropriate form type
		if (String.class.equals(method.getReturnType())) {
			// String type
			return new JTextField((String) method.invoke(object));

		} else if (Enum.class.isAssignableFrom(method.getReturnType())) {
			// Enum type (DIRECTION or DIRECTION_CHANGE)
			Object[] enums = null;
			if (method.getReturnType().equals(DIRECTION.class)) {
				enums = DIRECTION.values();

			} else if (method.getReturnType().equals(DIRECTION_CHANGE.class)) {
				enums = DIRECTION_CHANGE.values();
			}

			@SuppressWarnings({ "unchecked", "rawtypes" })
			final JComboBox comboBox = new JComboBox(enums);
			comboBox.setSelectedItem(method.invoke(object));

			return comboBox;

		} else if (method.getName().startsWith("is")) {
			// Boolean
			JCheckBox checkBox = new JCheckBox();
			checkBox.setSelected((Boolean) method.invoke(object));

			return checkBox;

		} else if (isInteger(method.getReturnType())) {
			// Integer (in textfield)
			integerFields.add(prop);
			return new JTextField(method.invoke(object).toString());

		}

		LOGGER.warn("Properties type {} is unknwon. Properties name = {}", method.getClass(), prop);
		throw new ViewException("Properties type " + method.getClass() + " is unknwon. Properties name = " + prop);
	}

	/**
	 * Test if class is integer...
	 *
	 * @param clazz
	 * @return
	 * @throws ClassNotFoundException
	 * @throws LinkageError
	 */
	private boolean isInteger(final Class<?> clazz) throws ClassNotFoundException, LinkageError {
		return int.class.equals(clazz);
	}

	/**
	 * Get getterMethod
	 *
	 * @param propertyName
	 * @return
	 * @throws NoSuchMethodException
	 */
	private Method getMethod(Class<?> clazz, final String propertyName) throws NoSuchMethodException {

		try {
			// Classic arg (String, Enum, ...)
			return clazz.getMethod("get" + StringUtils.capitalize(propertyName));

		} catch (NoSuchMethodException e) {
			// If no getter method, try 'is' method
			return clazz.getMethod("is" + StringUtils.capitalize(propertyName));
		}
	}

	/**
	 * Adding new line (label + field), after preview one. Align labels and fields.
	 *
	 * @param labelString
	 * @param component
	 */
	private void addLine(String labelString, JComponent component) {

		// Label
		final JLabel label = new JLabel(labelString + " : ");
		label.setMinimumSize(new Dimension(200, 20));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(label);

		layout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, getContentPane());
		layout.putConstraint(SpringLayout.EAST, label, 200, SpringLayout.WEST, getContentPane());
		if (null != topComponent) {
			layout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.SOUTH, topComponent);
			layout.putConstraint(SpringLayout.EAST, label, -5, SpringLayout.WEST, topComponent);
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
		topComponent = component;
	}
}
