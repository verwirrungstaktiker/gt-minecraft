package gt.general.trigger;

import java.util.Map;

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
	public TimeTrigger(final TriggerContext triggerContext) {
		super(triggerContext);
		
		//super(repeat,callback,tm);
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

	@Override
	public void setup(Map<String, Object> values) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> teardown() {
		// TODO Auto-generated method stub
		return null;
	}

}
