/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic.trigger;

import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.exceptions.PersistenceException;
import gt.general.world.BlockObserver;
import gt.general.world.ObservableCustomBlock;
import gt.general.world.ObservableCustomBlock.BlockEvent;
import gt.general.world.ObservableCustomBlock.BlockEventType;
import gt.plugin.meta.CustomBlockType;
import gt.plugin.meta.Hello;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class StepOnTrigger extends Trigger implements BlockObserver {
	
	private Block block;
	private boolean inUse;

	private int maximumContingent;
	private int currentContingent;
	
    private static final String KEY_BLOCK = "block";  
	private static final String KEY_CONTINGENT = "contingent";
    
    /**
     * construct a new trigger
     * @param block underlying block of the StepOnTrigger
     */
    public StepOnTrigger(final Block block) {
    	super("step_on_trigger");
    	
    	this.block = block;
    	this.inUse = false;
    	
    	CustomBlockType.STEP_ON_TRIGGER.place(block);
    	
    	registerWithSubject();
    	
		maximumContingent = -1;
		currentContingent = -1;    	
    }
    
    /**
     * construct a new StepOnTrigger
     */
    public StepOnTrigger() {}

	@Override
	public void setup(final PersistenceMap values, final World world)
			throws PersistenceException {
		
		block = values.getBlock(KEY_BLOCK, world);
		inUse = false;
		
		CustomBlockType.STEP_ON_TRIGGER.place(block);
		
		registerWithSubject();
		
		maximumContingent = values.getInt(KEY_CONTINGENT);
		currentContingent = maximumContingent;		

	}

	@Override
	public PersistenceMap dump() {
		PersistenceMap map = new PersistenceMap();
		
		map.put(KEY_BLOCK, block);
		map.put(KEY_CONTINGENT, maximumContingent);
		
		
		return map;
	}

	@Override
	public void dispose() {
		block.setType(Material.AIR);
		
		unregisterFromSubject();
	}
	
	/**
	 * registers this trigger
	 */
	private void registerWithSubject() {
		ObservableCustomBlock stepOnBlock = CustomBlockType.STEP_ON_TRIGGER.getCustomBlock();
		stepOnBlock.addObserver(this, block.getWorld());
	}
	
	/**
	 * unregisters this trigger
	 */
	private void unregisterFromSubject() {
		ObservableCustomBlock stepOnBlock = CustomBlockType.STEP_ON_TRIGGER.getCustomBlock();
		stepOnBlock.removeObserver(this, block.getWorld());
	}


	@Override
	public Set<Block> getBlocks() {
		Set<Block> blocks = new HashSet<Block>();
        blocks.add(block);
        
        return blocks;
	}

	@Override
	public void onBlockEvent(final BlockEvent blockEvent) {
		
		if(!inUse && blockEvent.getBlock().equals(block) && blockEvent.getBlockEventType() == BlockEventType.PLAYER_STEP_ON) {

			if(currentContingent != 0) {
				
				--currentContingent;
				inUse = true;
				CustomBlockType.STEP_ON_TRIGGER_DISABLED.place(block);
				
				block.getWorld().playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES, 25);
				getContext().updateTriggerState(this, true, blockEvent.getPlayer());
				
				if(currentContingent!=0) {
					Hello.scheduleOneTimeTask(new Runnable(){
						@Override
						public void run() {
							getContext().updateTriggerState(StepOnTrigger.this, false, blockEvent.getPlayer());
							inUse = false;
							CustomBlockType.STEP_ON_TRIGGER.place(block);
						}
					}, 20 * 2);
				}
				
			}
		}	
	}

}
