package gt.general.trigger;

import java.util.Vector;




public class TriggerManager implements Runnable {

	protected int i;
	protected Vector<Trigger> triggers = new Vector<Trigger>();
	
	public void registerTrigger(Trigger t) {
		triggers.add(t);
	}
	
	public void deregisterTrigger(Trigger t) {
		triggers.remove(t);
		--i;
	}
	
	@Override
	public void run() {
		
		for (i=0;i<triggers.capacity();++i) {
			triggers.get(i).checkTrigger();
		}
		
	}

}
