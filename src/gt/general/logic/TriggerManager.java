/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic;

import static com.google.common.collect.Sets.newHashSet;
import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.TriggerManagerPersistence;
import gt.general.logic.persistence.YamlSerializable;
import gt.general.logic.response.Response;
import gt.general.logic.trigger.Trigger;

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
	
	/**
	 * Adds a TriggerContext to the schedule
	 * @param context the TriggerContext to be scheduled
	 */
	public void addTriggerContext(final TriggerContext context) {
		triggerContexts.add(context);
	}
	
	/**
	 * Deletes a TriggerContext of the schedule
	 * @param context the TriggerContext to be deleted
	 */
	public void deleteTriggerContext(final TriggerContext context) {
		triggerContexts.remove(context);
	}

	@Override
	public void dispose() {
		for(TriggerContext triggerContext : triggerContexts) {
			triggerContext.dispose();
		}
		triggerContexts.clear();
	}

	@Override
	public void setup(final PersistenceMap values, final World world) {
		
		if(values != null) {
			TriggerManagerPersistence.setupTriggerManager(this, values, world);
		}
	}

	@Override
	public PersistenceMap dump() {
		return new PersistenceMap(TriggerManagerPersistence.dumpTriggerManager(this));
	}

	@Override
	public Set<Block> getBlocks() {
		Set<Block> blocks = newHashSet();
		for(TriggerContext context : triggerContexts) {
			for (Trigger trigger : context.getTriggers()) {
				blocks.addAll(trigger.getBlocks());
			}
			for (Response response : context.getResponses()) {
				blocks.addAll(response.getBlocks());
			}
		}
		
		return blocks;
	}
	
	/**
	 * Checks if all contexts are ready for saving
	 * @return true if all contexts can be saved
	 */
	public boolean canSave() {
		for (TriggerContext context : triggerContexts) {
			if (!context.isComplete()) {
				return false;
			}
		}
		
		return true;
	}

}
