package gt.general.logic.trigger;

import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.exceptions.PersistanceException;
import gt.general.world.BlockObserver;
import gt.general.world.ObservableCustomBlock.BlockEvent;
import gt.general.world.ObservableCustomBlock.BlockEventType;
import gt.plugin.meta.CustomBlockType;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

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
		//register?
	}
	
	/** to be used in persistance*/
	public GnomeTrigger() {}

    @Override
    public void setup(final PersistanceMap values, final World world)
            throws PersistanceException {
        
        block = values.getBlock(KEY_BLOCK, world);
        triggered = false;
        
        CustomBlockType.GNOME_TRIGGER_NEGATIVE.place(block);
        
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
        //unregister?
        
    }

    @Override
    public Set<Block> getBlocks() {
        Set<Block> blocks = new HashSet<Block>();
        blocks.add(block);
        
        return blocks;
    }

    @Override
    public void onBlockEvent(final BlockEvent blockEvent) {
        if(blockEvent.blockEventType == BlockEventType.BLOCK_INTERACT)
            triggered = !triggered;
            
            if(triggered) {
                CustomBlockType.GNOME_TRIGGER_POSITIVE.place(block);
            } else {
                CustomBlockType.GNOME_TRIGGER_NEGATIVE.place(block);
            }
            
    }

}
