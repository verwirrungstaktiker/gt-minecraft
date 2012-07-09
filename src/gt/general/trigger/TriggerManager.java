package gt.general.trigger;

import static com.google.common.collect.Sets.*;
import gt.general.trigger.persistance.PersistanceMap;
import gt.general.trigger.persistance.TriggerManagerPersistance;
import gt.general.trigger.persistance.YamlSerializable;

import java.util.Collection;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Manager for triggers
 */
public class TriggerManager extends YamlSerializable{

	public static final String PERSISTANCE_FILE = "trigger.yml";
	
	private final Set<TriggerContext> triggerContexts;
	
	public TriggerManager() {
		triggerContexts = newHashSet();
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

	@Override
	public void dispose() {
		for(TriggerContext triggerContext : triggerContexts) {
			triggerContext.dispose();
		}
	}

	@Override
	public void setup(final PersistanceMap values, final World world) {
		
		if(values != null) {
			TriggerManagerPersistance.setupTriggerManager(this, values, world);
		}
	}

	@Override
	public PersistanceMap dump() {
		return new PersistanceMap(TriggerManagerPersistance.dumpTriggerManager(this));
	}

	@Override
	public Set<Block> getBlocks() {
		// TODO Auto-generated method stub
		return null;
	}

}
