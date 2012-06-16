package gt.general.trigger;

import java.util.Collection;
import java.util.Vector;

/**
 * Manager for triggers
 */
public class TriggerManager implements Runnable {

	/**iterator, needed for in-loop-removal of Trigger*/
	private int i;
	/**Vector of all registered Triggers*/
	private Vector<Trigger> triggers = new Vector<Trigger>();

	/**
	 * @return Vector of all registered triggers
	 */
	public Vector<Trigger> getTriggers() {
		return triggers;
	}

	/**
	 * Registers a trigger
	 * @param t trigger to register
	 */
	public void registerTrigger(Trigger t) {
		triggers.add(t);
	}

	/**
	 * remove trigger from TriggerManager
	 * @param t the trigger to be removed
	 */
	public void deregisterTrigger(Trigger t) {
		triggers.remove(t);
		--i;
	}

	@Override
	public void run() {

		for (i=0;i<triggers.size();++i) {
			triggers.get(i).checkTrigger();
		}

	}

	public void dumpTrigger() {
		// TODO Auto-generated method stub
		
	}

	public Collection<TriggerContext> getContexts() {
		// TODO Auto-generated method stub
		return null;
	}

}
