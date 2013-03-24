package fr.dush.slalomgenerator.events.model;

/**
 * Model event types
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
public enum EventType {

	/** Delete model object event */
	DELETE,

	/** Update model object event */
	UPDATE,

	/** New model object event */
	NEW,

	/** Request to open pop-up to create new model object */
	CREATE_REQUEST,

	/** Request to generate sequence from GeneratorParameter */
	GENERATE;
}
