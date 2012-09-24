/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic.trigger;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockRedstoneEvent;

/**
 * Kind of Trigger that resets after some time
 */
public class ResettingRedstoneTrigger extends RedstoneTrigger{

	/**
	 * construct a new resetting trigger
	 */
	public ResettingRedstoneTrigger() {}
	
	/**
	 * @param string name prefix
	 * @param block block that holds the trigger
	 */
	public ResettingRedstoneTrigger(final String string, final Block block) {
		super (string, block);
	}

	/**
	 * @param event a redstone signal is inverted somewhere
	 */
	@EventHandler
	public void reset(final BlockRedstoneEvent event) {
		if (event.getBlock() == getBlock()) {
			if ( event.getNewCurrent() == 0) {
				setActive(false);
				getContext().updateTriggerState(this, getInverted() ^ false, null);
			}
		}
	}
}
