package gt.general.logic.trigger;

import gt.general.logic.TriggerContext;
import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.exceptions.PersistanceException;
import gt.plugin.meta.MultiListener;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 *  Implements the concept of using BlockRedstoneEvents as Triggers
 * 
 * @author Sebastian Fahnenschreiber
 */
public class RedstoneTrigger extends BlockTrigger implements Listener {	
	
	public static final String KEY_INVERTED = "inverted";
	
	private boolean inverted;
	
	private boolean active = false;
	
	/**
	 * @param prefix the prefix of the new trigger
	 * @param block THE block of the new trigger
	 */
	public RedstoneTrigger(final String prefix, final Block block) {
		super(prefix, block);
		MultiListener.registerListeners(this);
		inverted = false;
	}
	
	/** to be used in persistence */
	public RedstoneTrigger() {}

	/**
	 * @param event the redstone event this is based on
	 */
//	@EventHandler
//	public void onBlockRedstoneChange(final BlockRedstoneEvent event) {		
//		if(isBlockRedstoneEventHere(event)) {
//			boolean triggered = event.getNewCurrent() > 0;
//			
//			if (inverted) {
//				triggered = !triggered;
//			}
//			getContext().updateTriggerState(this, triggered);
//		}
//	}
	
	@EventHandler
	public void onPlayerInteract(final PlayerInteractEvent event) {
		
		if(getBlock().equals(event.getClickedBlock())) {
			
			active = ! active;
			
			getContext().updateTriggerState(this, inverted ^ active, event.getPlayer());
		}
	}
		
	
		
	/**
	 * @param event the redstone event to check
	 * @return true if the event is located on the block
	 */
	protected boolean isBlockRedstoneEventHere(final BlockRedstoneEvent event) {
		return getBlock().getLocation().equals(event.getBlock().getLocation());
	}
	
	@Override
	public void setup(final PersistanceMap values, final World world) throws PersistanceException {
		super.setup(values, world);
		
		MultiListener.registerListener(this);
		inverted = values.get(KEY_INVERTED);
	}
	
	@Override
	public PersistanceMap dump() {
		PersistanceMap map = super.dump();
		map.put(KEY_INVERTED, inverted);
		
		return map;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		MultiListener.unregisterListener(this);
	}
	
	@Override
	public void setContext(TriggerContext context) {
		super.setContext(context);
		
		if(inverted) {
			context.setupInverted(this);
		}
	}
	
	/**
	 * toggle the inverted state - invertception
	 */
	public void toggleInvert() {
		inverted = !inverted;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean getInverted() {
		return inverted;
	}

}
