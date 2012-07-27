package gt.general.logic.trigger;

import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.exceptions.PersistanceException;
import gt.general.world.BlockObserver;
import gt.general.world.ObservableCustomBlock;
import gt.general.world.ObservableCustomBlock.BlockEvent;
import gt.general.world.ObservableCustomBlock.BlockEventType;
import gt.lastgnome.GnomeItem;
import gt.plugin.meta.CustomBlockType;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.PlayerInventory;
import org.getspout.spoutapi.inventory.SpoutPlayerInventory;

import com.avaje.ebeaninternal.server.subclass.GetterSetterMethods;

/**
 *  
 * 
 * @author roman
 */
public class GnomeTrigger extends Trigger implements BlockObserver{
	
	private Block block;
	private boolean triggered;

    private static final String KEY_BLOCK = "block";
	
	/**
	 * @param block THE block of the trigger
	 */
	public GnomeTrigger(final Block block) {
	    super("gnome_trigger");
	    
		this.block = block;
		this.triggered = false;
		
		CustomBlockType.GNOME_TRIGGER_NEGATIVE.place(block);

		registerWithSubject();
	}
	
	/** to be used in persistance*/
	public GnomeTrigger() {}

    @Override
    public void setup(final PersistanceMap values, final World world)
            throws PersistanceException {
        
        block = values.getBlock(KEY_BLOCK, world);
        triggered = false;
        
        CustomBlockType.GNOME_TRIGGER_NEGATIVE.place(block);
        
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

    @Override
    public Set<Block> getBlocks() {
        Set<Block> blocks = new HashSet<Block>();
        blocks.add(block);
        
        return blocks;
    }
    
	/**
	 * registers this trigger
	 */
	private void registerWithSubject() {
		ObservableCustomBlock triggerBlock = CustomBlockType.GNOME_TRIGGER_NEGATIVE.getCustomBlock();
		triggerBlock.addObserver(this, block.getWorld());
	}
	
	/**
	 * unregisters this trigger
	 */
	private void unregisterFromSubject() {
		ObservableCustomBlock triggerBlock = CustomBlockType.GNOME_TRIGGER_NEGATIVE.getCustomBlock();
		triggerBlock.removeObserver(this, block.getWorld());
	}


    @Override
    public void onBlockEvent(final BlockEvent blockEvent) {
    	// don't bother if the block is being destroyed
    	if(blockEvent.blockEventType == BlockEventType.BLOCK_DESTROYED) {
    		return;
    	}

        if(blockEvent.blockEventType == BlockEventType.BLOCK_INTERACT) { 
	        PlayerInventory inv = blockEvent.player.getInventory();

        	//TODO this is a temporary fix as the gnome is flint underneath, as all CustomItems!
	        if(inv.getItemInHand().getType() == Material.FLINT) {
	            triggered = !triggered;
	            
	            if(triggered) {
	                CustomBlockType.GNOME_TRIGGER_POSITIVE.place(block);
	                getContext().updateTriggerState(GnomeTrigger.this, true);
	                
	            } else {
	                CustomBlockType.GNOME_TRIGGER_NEGATIVE.place(block);
	                getContext().updateTriggerState(GnomeTrigger.this, true);
	            }
	        } else {
	        	blockEvent.player.sendMessage(ChatColor.YELLOW + "You might need the gnome here");
	        }
        }
    }

}
