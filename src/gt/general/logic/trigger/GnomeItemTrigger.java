package gt.general.logic.trigger;

import gt.general.character.Hero;
import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.exceptions.PersistenceException;
import gt.general.world.BlockObserver;
import gt.general.world.ObservableCustomBlock;
import gt.general.world.ObservableCustomBlock.BlockEvent;
import gt.plugin.meta.CustomBlockType;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * @author roman
 */
public class GnomeItemTrigger extends ItemTrigger implements BlockObserver{

	/**
	 * @param block THE block of the trigger
	 */
	public GnomeItemTrigger(final Block block) {
	    super("gnome_trigger", block, UnlockItemType.GNOME);
		
	    registerWithSubject();
		CustomBlockType.GNOME_TRIGGER_NEGATIVE.place(block);
	}
	
	/**
	 * registers this trigger
	 */
	public void registerWithSubject() {
		ObservableCustomBlock triggerBlock = CustomBlockType.GNOME_TRIGGER_NEGATIVE.getCustomBlock();
		triggerBlock.addObserver(this, getBlock().getWorld());
	}
	
	/**
	 * unregisters this trigger
	 */
	public void unregisterFromSubject() {
		ObservableCustomBlock triggerBlock = CustomBlockType.GNOME_TRIGGER_NEGATIVE.getCustomBlock();
		triggerBlock.removeObserver(this, getBlock().getWorld());
	}
	
	/** to be used in persistence */
	public GnomeItemTrigger() {}

    @Override
    public void setup(final PersistenceMap values, final World world)
            throws PersistenceException {
    	super.setup(values, world);
        
    	registerWithSubject();
        CustomBlockType.GNOME_TRIGGER_NEGATIVE.place(getBlock());        
    }

	@Override
	protected void triggered(final BlockEvent event) {
		// nothing to do here
	}

	@Override
	protected boolean rightItemForTrigger(final Hero hero) {
		return hero.getActiveItem().getType() == getType();
	}


}
