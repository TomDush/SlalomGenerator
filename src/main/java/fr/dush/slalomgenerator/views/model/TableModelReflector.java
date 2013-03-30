package fr.dush.slalomgenerator.views.model;

import static com.google.common.collect.Lists.*;

import java.lang.reflect.Method;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.google.common.eventbus.Subscribe;

import fr.dush.slalomgenerator.events.model.FullChangeModel;
import fr.dush.slalomgenerator.events.model.confirmed.ConfirmDeleteEvent;
import fr.dush.slalomgenerator.events.model.confirmed.ConfirmEditEvent;
import fr.dush.slalomgenerator.events.model.confirmed.ConfirmNewEvent;
import fr.dush.slalomgenerator.exceptions.ReflexionException;

@SuppressWarnings("serial")
public class TableModelReflector<T> extends AbstractTableModel implements TableModel {

	private static final Logger LOGGER = LoggerFactory.getLogger(TableModelReflector.class);

	/** Internationalized strings */
	private ResourceBundle bundle;

	/** This table model is for this type */
	private Class<T> clazz;

	/** Object attributes to display (one by column) */
	private List<String> properties;

	/** Object list to display in table ; <code>this</code> is key words to get full object (used to display <i>actions</i> column)*/
	private List<T> objects;

	/** Boolean properties : getter is <code>is</code> and not <code>get</code>. */
	private List<String> booleanProperties = newArrayListWithCapacity(0);

	/**
	 * Initialize TableModel with all needed parameters. No one can be NULL !
	 *
	 * @param bundle
	 * @param clazz
	 * @param properties
	 * @param objects
	 */
	public TableModelReflector(ResourceBundle bundle, Class<T> clazz, List<String> properties, List<T> objects) {
		this.bundle = bundle;
		this.clazz = clazz;
		this.properties = properties;
		this.objects = objects;
	}

	/**
	 * Define properties which are boolean : no get method, but IS method.
	 *
	 * @param booleanProperties
	 */
	public void setBooleanProperties(List<String> booleanProperties) {
		this.booleanProperties = booleanProperties;
	}

	@Override
	public int getColumnCount() {
		return properties.size();
	}

	@Override
	public int getRowCount() {
		return objects.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		final String propertyName = getPropertyName(columnIndex);
		final T value = objects.get(rowIndex);

		try {
			if ("this".equals(propertyName)) return value;

			// Getter : get or is
			return getMethod(propertyName).invoke(value);

		} catch (Exception e) {
			LOGGER.error("Exception throw when trying to access to '{}' property of : {}.", propertyName, value);
			throw new ReflexionException("Error while reflect on : " + value, e);
		}
	}

	private Method getMethod(final String propertyName) throws NoSuchMethodException {
		final String accessor = booleanProperties.contains(propertyName) ? "is" : "get";

		return clazz.getMethod(accessor + StringUtils.capitalize(propertyName));
	}

	@Override
	public String getColumnName(int column) {
		return bundle.getString("table.column." + clazz.getSimpleName().toLowerCase() + "." + properties.get(column).toLowerCase());
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		final String columnName = getPropertyName(columnIndex);

		// Cas des boolean
		if (booleanProperties.contains(columnName)) return Boolean.class;

		// Autre, on va chercher...
		try {
			if ("this".equals(columnName)) return clazz;

			return getMethod(columnName).getReturnType();

		} catch (NoSuchMethodException e) {
			LOGGER.warn("Method for '{}' property not found in {}.", columnName, clazz);
			return super.getColumnClass(columnIndex);
		}

	}

	@Subscribe
	public void changeValues(FullChangeModel<T> event) {
		LOGGER.debug("Received FullChangeModel event for class {}. [this.clazz={}]", event.getClazz().getSimpleName(), clazz.getSimpleName());
		if (event.getClazz().equals(clazz)) {
			// Change values
			objects = newCopyOnWriteArrayList(event.getList());

			// Force table to be regenerate.
			fireTableDataChanged();
		}
	}

	@Subscribe
	public void objectEdited(ConfirmEditEvent event) {
		// If this table model treat event's type
		if (clazz.equals(event.getClazz())) {
			final int index = objects.indexOf(event.getObject());
			fireTableRowsUpdated(index, index);
		}
	}

	@SuppressWarnings("unchecked")
	@Subscribe
	public void objectAdded(ConfirmNewEvent event) {
		// If this table model treat event's type
		if (clazz.equals(event.getClazz())) {
			objects.add((T) event.getObject());
			fireTableRowsInserted(objects.size() - 1, objects.size()-1);
		}
	}

	@Subscribe
	public void objectDeleted(ConfirmDeleteEvent event) {
		// If this table model treat event's type
		if (clazz.equals(event.getClazz())) {
			final int index = objects.indexOf(event.getObject());
			if (-1 != index && null != objects.remove(index)) {
				fireTableRowsDeleted(index, index);
			}
		}
	}

	/**
	 * Return property name.
	 *
	 * @param columnIndex
	 * @return
	 */
	private String getPropertyName(int columnIndex) {
		return properties.get(columnIndex);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return 0 == columnIndex;
	}

}
