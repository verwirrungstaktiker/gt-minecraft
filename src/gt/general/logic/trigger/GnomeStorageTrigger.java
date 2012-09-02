package gt.general.logic.trigger;

import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.exceptions.PersistenceException;
import gt.general.world.BlockObserver;
import gt.general.world.ObservableCustomBlock;
import gt.general.world.ObservableCustomBlock.BlockEvent;
import gt.general.world.ObservableCustomBlock.BlockEventType;
import gt.lastgnome.GnomeItem;
import gt.plugin.meta.CustomBlockType;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class GnomeStorageTrigger extends BlockTrigger implements BlockObserver{

	private boolean triggered;
	private final static UnlockItemType TYPE = UnlockItemType.GNOME;
	private GnomeItem gnome;
	
	/**
	 * @param block the block of the trigger
	 * @param name the name prefix of the trigger
	 * @param type type of item that is needed to trigger
	 */
	public GnomeStorageTrigger(final String name, final Block block) {
		super(name, block);
		
		this.triggered = false;

		registerWithSubject();
	}
	
	/** to be used in persistence*/
	public GnomeStorageTrigger() {}
	
	/**
	 * registers this trigger
	 */
	public void registerWithSubject() {
		ObservableCustomBlock triggerBlock = CustomBlockType.GNOME_TRIGGER_NEGATIVE.getCustomBlock();
		triggerBlock.addObserver(this, getBlock().getWorld());

		triggerBlock = CustomBlockType.GNOME_TRIGGER_POSITIVE.getCustomBlock();
		triggerBlock.addObserver(this, getBlock().getWorld());
	}
	
	/**
	 * unregisters this trigger
	 */
	public void unregisterFromSubject() {
		ObservableCustomBlock triggerBlock = CustomBlockType.GNOME_TRIGGER_NEGATIVE.getCustomBlock();
		triggerBlock.removeObserver(this, getBlock().getWorld());

		triggerBlock = CustomBlockType.GNOME_TRIGGER_POSITIVE.getCustomBlock();
		triggerBlock.removeObserver(this, getBlock().getWorld());
	}

    @Override
    public Set<Block> getBlocks() {
        return super.getBlocks();
    }
	
    @Override
    public void setup(final PersistenceMap values, final World world)
            throws PersistenceException {
    	super.setup(values, world);
    	
        triggered = false;
        CustomBlockType.GNOME_TRIGGER_NEGATIVE.place(getBlock());        
        //state = CustomBlockType.GNOME_TRIGGER_NEGATIVE.getCustomBlock();       
        registerWithSubject();
        
    }
    
    @Override
    public PersistenceMap dump() {
        PersistenceMap map = super.dump();
        
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
        	
        	Player player = blockEvent.getPlayer();
	        Hero hero = HeroManager.getHero(player);

	        if(!triggered && hero!=null && hero.getActiveItem()!=null 
	        		&& hero.getActiveItem().getType() == TYPE) {
	            triggered = true;
	   
                CustomBlockType.GNOME_TRIGGER_POSITIVE.place(getBlock());
	        	blockEvent.getPlayer().sendMessage(ChatColor.YELLOW + "Gnome stored");
	        	
	        	gnome = (GnomeItem) hero.removeActiveItem();
          
	        } else if(triggered && hero!=null && hero.getActiveItem() == null) {
	            triggered = false;
	   
                CustomBlockType.GNOME_TRIGGER_NEGATIVE.place(getBlock());
	        	blockEvent.getPlayer().sendMessage(ChatColor.YELLOW + "Gnome retrieved");
	        	
	        	hero.setActiveItem(gnome);	        	
	        }
	        
	        getContext().updateTriggerState(this, true, blockEvent.getPlayer());
        }
    }
    
    

}
