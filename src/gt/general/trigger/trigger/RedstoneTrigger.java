package gt.general.trigger.trigger;

import gt.general.trigger.TriggerContext;
import gt.general.trigger.persistance.PersistanceMap;
import gt.plugin.meta.MultiListener;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

/**
 *  Implements the concept of using BlockRedstoneEvents as Triggers
 * 
 * @author Sebastian Fahnenschreiber
 */
public abstract class RedstoneTrigger extends BlockTrigger implements Listener {	
	
	public static final String KEY_INVERTED = "inverted";
	
	private boolean inverted;
	
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
	@EventHandler
	public void onBlockRedstoneChange(final BlockRedstoneEvent event) {		
		if(isBlockRedstoneEventHere(event)) {
			boolean triggered = event.getNewCurrent() > 0;
			
			if (inverted) {
				triggered = !triggered;
			}
			getContext().updateTriggerState(this, triggered);
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
	public void setup(final PersistanceMap values, final World world) {
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
	
	//Needed to activate inverted levers on startup
	@Override
	public void setContext(final TriggerContext context) {
		super.setContext(context);
		//System.out.println("");
		BlockRedstoneEvent event = new BlockRedstoneEvent(getBlock(), 0,getBlock().getBlockPower());
		onBlockRedstoneChange(event);
	}
	
	/**
	 * inverts the inverted state - invertception
	 */
	public void toggleInvert() {
		inverted = !inverted;
	}

}
