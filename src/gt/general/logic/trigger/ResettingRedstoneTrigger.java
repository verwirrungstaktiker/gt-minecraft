package gt.general.logic.trigger;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockRedstoneEvent;

public class ResettingRedstoneTrigger extends RedstoneTrigger{

	public ResettingRedstoneTrigger() {}
	
	public ResettingRedstoneTrigger(String string, Block block) {
		super (string, block);
	}

	@EventHandler
	public void reset(BlockRedstoneEvent event) {
		if (event.getBlock() == getBlock()) {
			if ( event.getNewCurrent() == 0) {
				setActive(false);
				getContext().updateTriggerState(this, getInverted() ^ false, null);
			}
		}
	}
}
