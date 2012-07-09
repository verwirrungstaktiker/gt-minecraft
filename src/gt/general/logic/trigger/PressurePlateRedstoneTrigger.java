package gt.general.logic.trigger;

import org.bukkit.block.Block;

/**
 * Uses a pressure block as trigger (wood or stone)
 * 
 * @author Sebastian Fahnenschreiber
 */
public class PressurePlateRedstoneTrigger extends RedstoneTrigger {
	
	/**
	 * @param pressurePlate the plate to be used as trigger
	 */
	public PressurePlateRedstoneTrigger(final Block pressurePlate) {
		super("pressure_plate", pressurePlate);
	}
	
	/** to be used for persistance */
	public PressurePlateRedstoneTrigger() {}
}
