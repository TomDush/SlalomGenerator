package fr.dush.slalomgenerator.views.model;

/**
 * Provide specific display to table column.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
public interface IColumnDisplayer<Source, Parameter> {

	/**
	 * Display value parameter.
	 *
	 * @param object Parameter owner
	 * @param value Parameter to display
	 * @return Widget to represent value.
	 */
	Object display(Source object, Parameter value);
}
