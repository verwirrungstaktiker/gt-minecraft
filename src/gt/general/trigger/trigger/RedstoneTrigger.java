package gt.general.trigger.trigger;

import gt.general.trigger.TriggerContext;
import gt.general.trigger.persistance.PersistanceMap;
import gt.plugin.meta.MultiListener;

import java.util.Map;

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
	
	public final static String KEY_INVERTED = "inverted";
	
	private boolean inverted;
	
	public RedstoneTrigger(String prefix, Block block) {
		super(prefix, block);
		MultiListener.registerListeners(this);
		inverted = false;
	}
	
	public RedstoneTrigger() {
		super();
		MultiListener.registerListeners(this);
	}

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
	public void setContext(TriggerContext context) {
		super.setContext(context);
		//System.out.println("");
		BlockRedstoneEvent event = new BlockRedstoneEvent(getBlock(), 0,getBlock().getBlockPower());
		onBlockRedstoneChange(event);
	}
	
	public void toggleInvert() {
		inverted = !inverted;
	}

}
