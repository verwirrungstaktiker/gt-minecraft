/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic.trigger;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.exceptions.PersistenceException;
import gt.general.world.BlockObserver;
import gt.general.world.ObservableCustomBlock.BlockEvent;
import gt.general.world.ObservableCustomBlock.BlockEventType;
import gt.plugin.meta.CustomBlockType;

/**
 * represents a Trigger that can only be triggered if you wield a special item
 * @author Roman
 *
 */
public abstract class ItemTrigger extends BlockTrigger implements BlockObserver{

	public static final String KEY_TYPE = "type";
	
	private boolean triggered;
	
	private UnlockItemType unlockType;
	private CustomBlockType customType;

	/**
	 * @param block the block of the trigger
	 * @param name the name prefix of the trigger
	 * @param type type of item that is needed to trigger
	 */
	public ItemTrigger(final String name, final Block block, final UnlockItemType type) {
		super(name, block);
		
		this.triggered = false;
		this.unlockType = type;
	}
	
	/** to be used in persistence*/
	public ItemTrigger() {}
	
	/**
	 * registers this trigger
	 */
	public abstract void registerWithSubject();
	
	/**
	 * unregisters this trigger
	 */
	public abstract void unregisterFromSubject();

    @Override
    public Set<Block> getBlocks() {
        return super.getBlocks();
    }
	
    @Override
    public void setup(final PersistenceMap values, final World world)
            throws PersistenceException {
    	super.setup(values, world);
    	
    	unlockType = UnlockItemType.valueOf((String) values.get(KEY_TYPE));

        triggered = false;
        
    }
    
    @Override
    public PersistenceMap dump() {
        PersistenceMap map = super.dump();

        map.put(KEY_TYPE, unlockType.toString());
        
		return map;
    }
	

    @Override
    public void dispose() {
    	super.dispose();
    	
        getBlock().setType(Material.AIR);

        unregisterFromSubject();
        
    }
    

    @Override
    public void onBlockEvent(final BlockEvent blockEvent) {
    	// don't bother if the block is being destroyed
    	if(blockEvent.getBlockEventType() == BlockEventType.BLOCK_DESTROYED) {
    		return;
    	}

        if(blockEvent.getBlockEventType() == BlockEventType.BLOCK_INTERACT && blockEvent.getBlock()==getBlock()) { 

	        Hero hero = HeroManager.getHero(blockEvent.getPlayer());

	        if(hero!=null && hero.getActiveItem()!=null && rightItemForTrigger(hero)) {
	            triggered = !triggered;
	            
	            if(triggered) {
	                CustomBlockType.GNOME_TRIGGER_POSITIVE.place(getBlock());
	                getContext().updateTriggerState(this, true, blockEvent.getPlayer());
	                triggered(blockEvent);
	            } else {
	                CustomBlockType.GNOME_TRIGGER_NEGATIVE.place(getBlock());
	                getContext().updateTriggerState(this, true, blockEvent.getPlayer());
	            }
	        } else {
	        	blockEvent.getPlayer().sendMessage(ChatColor.YELLOW + "You might need a special item here");
	        }
        }
    }

    /**
     * @param hero Hero that triggers the trigger
     * @return true if the Heroes item fits the lock of the Trigger
     */
    protected abstract boolean rightItemForTrigger(Hero hero);

	/**
     * additional action that shall be performed when triggered
     * @param event event that caused the trigger
     */
	protected abstract void triggered(final BlockEvent event);
	
	/**
	 * @return get the kind of "key" that fits the Trigger
	 */
	public UnlockItemType getType() {
		return unlockType;
	}

	/**
	 * @return The visual kind of block
	 */
	public CustomBlockType getCustomType() {
		return customType;
	}

	/**
	 * @param customType The visual kind of block
	 */
	public void setCustomType(final CustomBlockType customType) {
		this.customType = customType;
	}

}
