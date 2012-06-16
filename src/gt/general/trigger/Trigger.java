package gt.general.trigger;

/**
 * Abstract class to build triggers
 */
public abstract class Trigger implements YamlSerializable {

	protected TriggerContext context;
	protected String label;
	/**
	 * Creates a trigger
	 * @param repeat false, if the Trigger should only be triggered once
	 */
	public Trigger (TriggerContext context) {
		this.context = context;
		context.addTrigger(this);
	}
	
	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void setLabel(String label) {
		this.label = label;
	}
		

	/**
	 * checks trigger-condition and calls callback if appropriate
	 */
	public abstract void checkTrigger();

}
