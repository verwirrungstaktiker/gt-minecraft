/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
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
import gt.lastgnome.game.LastGnomeGame;
import gt.plugin.meta.CustomBlockType;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class GnomeStorageTrigger extends BlockTrigger implements BlockObserver{

	private boolean triggered;
	private static final UnlockItemType TYPE = UnlockItemType.GNOME;
	private GnomeItem gnome;
	
	/**
	 * @param block the block of the trigger
	 */
	public GnomeStorageTrigger(final Block block) {
		super("gnome_trigger", block);
		
		this.triggered = false;

		registerWithSubject();
	}

	
	/** to be used in persistence*/
	public GnomeStorageTrigger() {}
	
	/**
	 * registers this trigger
	 */
	public void registerWithSubject() {
		ObservableCustomBlock triggerBlock = CustomBlockType.GNOME_STORAGE_NEGATIVE.getCustomBlock();
		triggerBlock.addObserver(this, getBlock().getWorld());

		triggerBlock = CustomBlockType.GNOME_STORAGE_POSITIVE.getCustomBlock();
		triggerBlock.addObserver(this, getBlock().getWorld());
	}
	
	/**
	 * unregisters this trigger
	 */
	public void unregisterFromSubject() {
		ObservableCustomBlock triggerBlock = CustomBlockType.GNOME_STORAGE_NEGATIVE.getCustomBlock();
		triggerBlock.removeObserver(this, getBlock().getWorld());

		triggerBlock = CustomBlockType.GNOME_STORAGE_POSITIVE.getCustomBlock();
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
        CustomBlockType.GNOME_STORAGE_NEGATIVE.place(getBlock());        
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
	   
                CustomBlockType.GNOME_STORAGE_POSITIVE.place(getBlock());
	        	blockEvent.getPlayer().sendMessage(ChatColor.YELLOW + "Gnome stored");
	        	
	        	gnome = (GnomeItem) hero.removeActiveItem();
	        	LastGnomeGame lgg = (LastGnomeGame)hero.getGame();
	        	lgg.setGnomeBearer(null);
          
	        } else if(triggered && hero!=null && hero.getActiveItem() == null) {
	            triggered = false;
	   
                CustomBlockType.GNOME_STORAGE_NEGATIVE.place(getBlock());
	        	blockEvent.getPlayer().sendMessage(ChatColor.YELLOW + "Gnome retrieved");
	        	
	        	hero.setActiveItem(gnome);	
	        	LastGnomeGame lgg = (LastGnomeGame)hero.getGame();
	        	lgg.setGnomeBearer(hero);
	        }
	        
	        getContext().updateTriggerState(this, true, blockEvent.getPlayer());
        }
    }
    
    

}
