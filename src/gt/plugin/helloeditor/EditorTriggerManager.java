package gt.plugin.helloeditor;

import gt.general.trigger.TriggerContext;
import gt.general.trigger.TriggerManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.block.Block;

/**
 * Manager for triggers
 */
public class EditorTriggerManager extends TriggerManager {
	
	Map<Block, TriggerContext> blockToContext = new HashMap<Block, TriggerContext>();

	
	public EditorTriggerManager() {
		super();
	}
	
	public EditorTriggerManager(final Set<TriggerContext> triggerContexts) {
		super(triggerContexts);
		//TODO: sync maps
	}
	
	
	public void addTriggerContext(final TriggerContext context) {
		super.addTriggerContext(context);
		addContextMapping(context);
	}
	
	private void addContextMapping(TriggerContext context) {
		//TODO: implement
	}

	public void deleteTriggerContext(final TriggerContext context) {
		super.deleteTriggerContext(context);
		removeContextMapping(context);
	}

	private void removeContextMapping(TriggerContext context) {
		// TODO Auto-generated method stub
	}

	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
