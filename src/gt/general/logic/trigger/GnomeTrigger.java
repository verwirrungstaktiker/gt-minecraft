package gt.general.logic.trigger;

import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.exceptions.PersistanceException;
import gt.general.world.BlockObserver;
import gt.general.world.ObservableCustomBlock;
import gt.general.world.ObservableCustomBlock.BlockEvent;
import gt.plugin.meta.CustomBlockType;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * @author roman
 */
public class GnomeTrigger extends ItemTrigger implements BlockObserver{

	/**
	 * @param block THE block of the trigger
	 */
	public GnomeTrigger(final Block block) {
	    super("gnome_trigger", block, ItemType.GNOME);
		
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
	public GnomeTrigger() {}

    @Override
    public void setup(final PersistanceMap values, final World world)
            throws PersistanceException {
    	super.setup(values, world);
        
        CustomBlockType.GNOME_TRIGGER_NEGATIVE.place(getBlock());        
    }

	@Override
	protected void triggered(final BlockEvent event) {
		Hero hero = HeroManager.getHero(event.getPlayer());
		
		hero.removeActiveItem();
	}


}
