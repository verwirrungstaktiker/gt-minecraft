package gt.general.trigger;


public abstract class Trigger {
	
	protected boolean repeat;
	protected Runnable callback;
	protected TriggerManager tm;
	
	public Trigger (boolean repeat, Runnable callback, TriggerManager tm) {
		this.repeat = repeat;
		this.callback = callback;
		this.tm = tm;
		tm.registerTrigger(this);
	}
	
	public abstract void checkTrigger();

}
