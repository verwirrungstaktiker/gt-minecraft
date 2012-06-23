package gt.general.trigger;

import gt.plugin.listener.MultiListener;

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
public abstract class RedstoneTrigger extends Trigger implements Listener {	
	
	public RedstoneTrigger(String prefix) {
		super(prefix);
		MultiListener.registerListeners(this);
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
		if(getBlock().getLocation().equals(event.getBlock().getLocation())) {
			
			getContext().updateTriggerState(this, event.getNewCurrent() > 0);
		}
	}
	
	/**
	 * @return the block to be watched
	 */
	protected abstract Block getBlock();
	
	@Override
	public void setup(final Map<String, Object> values, final World world) {
		MultiListener.registerListener(this);
	}
	
	@Override
	public void dispose() {
		MultiListener.unregisterListener(this);
	}

}
