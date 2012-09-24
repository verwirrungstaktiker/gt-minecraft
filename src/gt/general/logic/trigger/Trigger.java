/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic.trigger;

import gt.general.logic.TriggerContext;
import gt.general.logic.persistence.YamlSerializable;

/**
 * Abstract class to build triggers
 */
public abstract class Trigger extends YamlSerializable {

	private TriggerContext context;
	
	/**
	 * Creates a trigger
	 * @param labelPrefix the prefix for the new label
	 */
	public Trigger (final String labelPrefix) {
		super(labelPrefix);
	}
	
	/** to be used in persistence */
	public Trigger () {}

	/**
	 * @return who to notify if this triggers
	 */
	public TriggerContext getContext() {
		return context;
	}

	/**
	 * @param context who to notify if this triggers
	 */
	public void setContext(final TriggerContext context) {
		this.context = context;
	}
}
