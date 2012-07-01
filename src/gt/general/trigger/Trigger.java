package gt.general.trigger;

import gt.general.trigger.persistance.YamlSerializable;

/**
 * Abstract class to build triggers
 */
public abstract class Trigger extends YamlSerializable {

	private TriggerContext context;
	
	/**
	 * Creates a trigger
	 */
	public Trigger (final String labelPrefix) {
		super(labelPrefix);
	}
	
	public Trigger () {
		
	}

	public TriggerContext getContext() {
		return context;
	}

	public void setContext(TriggerContext context) {
		this.context = context;
	}

	public abstract void highlight();

}
