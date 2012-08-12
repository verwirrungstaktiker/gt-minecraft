package gt.general.logic.trigger;

import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.exceptions.PersistanceException;
import gt.general.world.BlockObserver;
import gt.general.world.ObservableCustomBlock;
import gt.general.world.ObservableCustomBlock.BlockEvent;
import gt.general.world.ObservableCustomBlock.BlockEventType;
import gt.plugin.meta.CustomBlockType;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.PlayerInventory;

/**
 *  
 * 
 * @author roman
 */
public class GnomeTrigger extends BlockTrigger implements BlockObserver{

	private boolean triggered;
	
	/**
	 * @param block THE block of the trigger
	 */
	public GnomeTrigger(final Block block) {
	    super("gnome_trigger", block);

		this.triggered = false;
		
		CustomBlockType.GNOME_TRIGGER_NEGATIVE.place(block);

		registerWithSubject();
	}
	
	/** to be used in persistance*/
	public GnomeTrigger() {}

    @Override
    public void setup(final PersistanceMap values, final World world)
            throws PersistanceException {
    	super.setup(values, world);

        triggered = false;
        
        CustomBlockType.GNOME_TRIGGER_NEGATIVE.place(getBlock());
        
        registerWithSubject();
        
    }

    @Override
    public PersistanceMap dump() {
        return super.dump();
    }

    @Override
    public void dispose() {
    	super.dispose();
    	
        getBlock().setType(Material.AIR);

        unregisterFromSubject();
        
    }

    @Override
    public Set<Block> getBlocks() {
        return super.getBlocks();
    }
    
	/**
	 * registers this trigger
	 */
	private void registerWithSubject() {
		ObservableCustomBlock triggerBlock = CustomBlockType.GNOME_TRIGGER_NEGATIVE.getCustomBlock();
		triggerBlock.addObserver(this, getBlock().getWorld());
	}
	
	/**
	 * unregisters this trigger
	 */
	private void unregisterFromSubject() {
		ObservableCustomBlock triggerBlock = CustomBlockType.GNOME_TRIGGER_NEGATIVE.getCustomBlock();
		triggerBlock.removeObserver(this, getBlock().getWorld());
	}


    @Override
    public void onBlockEvent(final BlockEvent blockEvent) {
    	// don't bother if the block is being destroyed
    	if(blockEvent.blockEventType == BlockEventType.BLOCK_DESTROYED) {
    		return;
    	}

        if(blockEvent.blockEventType == BlockEventType.BLOCK_INTERACT && blockEvent.block==getBlock()) { 
	        PlayerInventory inv = blockEvent.player.getInventory();

        	//TODO this is a temporary fix as the gnome is flint underneath, as all CustomItems!
	        if(inv.getItemInHand().getType() == Material.FLINT) {
	            triggered = !triggered;
	            
	            if(triggered) {
	                CustomBlockType.GNOME_TRIGGER_POSITIVE.place(getBlock());
	                getContext().updateTriggerState(this, true, blockEvent.player);
	                
	            } else {
	                CustomBlockType.GNOME_TRIGGER_NEGATIVE.place(getBlock());
	                getContext().updateTriggerState(this, true, blockEvent.player);
	            }
	        } else {
	        	blockEvent.player.sendMessage(ChatColor.YELLOW + "You might need the gnome here");
	        }
        }
    }

}
