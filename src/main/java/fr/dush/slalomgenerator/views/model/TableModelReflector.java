package fr.dush.slalomgenerator.views.model;

import static com.google.common.collect.Lists.*;

import java.util.List;
import java.util.ResourceBundle;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

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

	/** Object list to display in table */
	private List<T> objects;

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
	 * Define properties which are boolean : no get method, but IS méthod.
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
		final String propertyName = properties.get(columnIndex);

		try {
			final String accessor = booleanProperties.contains(propertyName) ? "is" : "get";
			return clazz.getMethod(accessor + StringUtils.capitalize(propertyName)).invoke(objects.get(rowIndex));

		} catch (Exception e) {
			LOGGER.error("Exception throw when trying to access to '{}' property of : {}.", propertyName, objects.get(rowIndex));
			throw new ReflexionException("Error while reflect on : " + objects.get(rowIndex), e);
		}
	}

	@Override
	public String getColumnName(int column) {
		return bundle.getString("table.column." + clazz.getSimpleName().toLowerCase() + "." + properties.get(column).toLowerCase());
	}

}
