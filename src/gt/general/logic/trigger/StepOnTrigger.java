package gt.general.logic.trigger;

import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.exceptions.PersistanceException;
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
	private boolean triggered;
	private boolean inUse;

    private static final String KEY_BLOCK = "block";
    
    public StepOnTrigger(final Block block) {
    	super("step_on_trigger");
    	
    	this.block = block;
    	this.triggered = false;
    	this.inUse = false;
    	
    	CustomBlockType.STEP_ON_TRIGGER.place(block);
    	
    	registerWithSubject();
    	
    }
    
    public StepOnTrigger() {}

	@Override
	public void setup(final PersistanceMap values, final World world)
			throws PersistanceException {
		
		block = values.getBlock(KEY_BLOCK, world);
		triggered = false;
		inUse = false;
		
		CustomBlockType.STEP_ON_TRIGGER.place(block);
		
		registerWithSubject();

	}

	@Override
	public PersistanceMap dump() {
		PersistanceMap map = new PersistanceMap();
		
		map.put(KEY_BLOCK, block);
		
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
			
			//block.getWorld().playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES, 25);
            getContext().updateTriggerState(this, true, blockEvent.getPlayer());
            
            Hello.scheduleOneTimeTask(new Runnable() {
				
				@Override
				public void run() {
					getContext().updateTriggerState(StepOnTrigger.this, false, blockEvent.getPlayer());
				}
			}, 20 * 2);
        }
	}

}
