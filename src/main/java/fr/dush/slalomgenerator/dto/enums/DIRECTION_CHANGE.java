package fr.dush.slalomgenerator.dto.enums;

/**
 * Change (or not) rider's direction.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
public enum DIRECTION_CHANGE {

	/** Rider keeps his direction */
	KEEP,

	/** Rider inverses his direction */
	CHANGE,

	/** Rider can change his direction, or keep it */
	NEITHER;

}
