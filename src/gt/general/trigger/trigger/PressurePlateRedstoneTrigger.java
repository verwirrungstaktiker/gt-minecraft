package gt.general.trigger.trigger;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.World;
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
	
	
	public PressurePlateRedstoneTrigger() {
		super();
	}
}
