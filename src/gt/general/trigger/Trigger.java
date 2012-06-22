package gt.general.trigger;

import gt.general.trigger.persistance.YamlSerializable;

/**
 * Abstract class to build triggers
 */
public abstract class Trigger extends YamlSerializable {

	private TriggerContext context;
	/**
	 * Creates a trigger
	 * @param repeat false, if the Trigger should only be triggered once
	 */
	public Trigger (final TriggerContext context) {
		setContext(context);
	}
	
	public Trigger () {
		
	}

	public TriggerContext getContext() {
		return context;
	}

	public void setContext(TriggerContext context) {
		this.context = context;
	}

}
