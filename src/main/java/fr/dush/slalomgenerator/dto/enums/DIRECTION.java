package fr.dush.slalomgenerator.dto.enums;

import lombok.Getter;

/**
 * Rider's direction : forward, back ward.
 *
 * @author Thomas Duchatelle (tomdush@gmail.com)
 *
 */
@Getter
public enum DIRECTION {

	/** Forward direction */
	FORWARD(0x01),

	/** Backward direction */
	BACKWARD(0x10),

	/** Can be both */
	INCONSEQUENTIAL(0x11);

	/** Binary mask : bit 0 = FORWARD, bit 1 = BACKWARD */
	private int mask;

	private DIRECTION(int mask) {
		this.mask = mask;
	}

	public boolean isCompatible(DIRECTION direction) {
		return (direction.getMask() & getMask()) != 0;
	}

	/**
	 * Inverse direction. INCONSEQUENTIAL stay same.
	 *
	 * @return BACKWARD if FORWARD ; FORWARD if BACKWARD ; else INCONSEQUENTIAL
	 */
	public DIRECTION inverse() {
		if (INCONSEQUENTIAL == this) return this;

		return this == DIRECTION.FORWARD ? DIRECTION.BACKWARD : DIRECTION.FORWARD;
	}
}
