package gt.general.trigger;

/**
 * Trigger which is called in an interval
 */
public class TimeTrigger extends Trigger{

	protected int count,interval;

	/**
	 *
	 * @param interval time in TriggerManager-ticks
	 * @param repeat false, if the Trigger should only be triggered once
	 * @param callback runnable to be called
	 * @param tm the TriggerManager for this trigger
	 */
	public TimeTrigger (int interval, boolean repeat, Runnable callback, TriggerManager tm) {
		super(repeat,callback,tm);
		this.interval = interval;
		this.count = interval;
	}

	@Override
	public void checkTrigger() {
		--count;
		if (count == 0) {
			callback.run();
			if (repeat) count = interval;
			else tm.deregisterTrigger(this);
		}

	}

}
