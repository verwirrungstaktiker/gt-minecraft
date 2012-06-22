package gt.general.trigger;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Manager for triggers
 */
public class TriggerManager {

	private final Set<TriggerContext> triggerContexts;
	
	public TriggerManager() {
		triggerContexts = new HashSet<TriggerContext>();
	}
	
	public TriggerManager(final Set<TriggerContext> triggerContexts) {
		this.triggerContexts = triggerContexts;
	}
	

	public Collection<TriggerContext> getTriggerContexts() {
		return triggerContexts;
	}
	
	public void addTriggerContext(TriggerContext context) {
		triggerContexts.add(context);
	}
	
	public void deleteTriggerContext(TriggerContext context) {
		triggerContexts.remove(context);
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
