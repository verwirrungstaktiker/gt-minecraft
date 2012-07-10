package gt.general.logic;

import static com.google.common.collect.Sets.newHashSet;
import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.TriggerManagerPersistance;
import gt.general.logic.persistance.YamlSerializable;

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
	
	/**
	 * creates a new TriggerManager
	 */
	public TriggerManager() {
		triggerContexts = newHashSet();
	}
	
	/**
	 * create a new TriggerManager
	 * @param triggerContexts the registered triggers/responses
	 */
	public TriggerManager(final Set<TriggerContext> triggerContexts) {
		this.triggerContexts = triggerContexts;
	}
	
	/**
	 * @return scheduled triggers/responses
	 */
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
