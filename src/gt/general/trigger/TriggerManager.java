package gt.general.trigger;

import java.util.Vector;




public class TriggerManager implements Runnable {

	protected Vector<Runnable> triggers = new Vector<Runnable>();
	
	public void registerTrigger(Runnable t) {
		triggers.add(t);
	}
	
	public void deregisterTrigger(Runnable t) {
		triggers.remove(t);
	}
	
	@Override
	public void run() {
		for (int i=0;i<triggers.capacity();++i) {
			triggers.get(i).run();
		}
		
	}

}
