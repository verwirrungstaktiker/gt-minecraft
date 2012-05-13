package gt.general.trigger;

public class TimeTrigger extends Trigger{

	protected int count,interval;

	/**
	 * 
	 * @param interval 
	 * @param repeat true if the trigger should be repeatable
	 * @param tm the TriggerManager
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
