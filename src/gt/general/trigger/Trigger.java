package gt.general.trigger;

import gt.general.trigger.persistance.YamlSerializable;

/**
 * Abstract class to build triggers
 */
public abstract class Trigger implements YamlSerializable {

	private TriggerContext context;
	private String label;
	/**
	 * Creates a trigger
	 * @param repeat false, if the Trigger should only be triggered once
	 */
	public Trigger (final TriggerContext context) {
		setContext(context);
	}
	
	public Trigger () {
		
	}
	
	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void setLabel(final String label) {
		this.label = label;
	}

	public TriggerContext getContext() {
		return context;
	}

	public void setContext(TriggerContext context) {
		this.context = context;
	}

}
