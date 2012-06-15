package gt.general.trigger;

/**
 * Abstract class to build triggers
 */
public abstract class Trigger {

	/**boolean if the Trigger is repeatable*/
	protected boolean repeat;
	/**callback to run, if the Trigger triggers*/
	protected Runnable callback;
	/**TriggerManager this Trigger belongs to*/
	protected TriggerManager tm;
	
	/**
	 * Creates a trigger
	 * @param repeat false, if the Trigger should only be triggered once
	 * @param callback runnable to be called
	 * @param tm the TriggerManager for this trigger
	 */
	public Trigger (boolean repeat, Runnable callback, TriggerManager tm) {
		this.repeat = repeat;
		this.callback = callback;
		this.tm = tm;
		tm.registerTrigger(this);
	}

	/**
	 * checks trigger-condition and calls callback if appropriate
	 */
	public abstract void checkTrigger();

}
