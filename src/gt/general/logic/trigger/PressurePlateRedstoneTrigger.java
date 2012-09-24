/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic.trigger;

import org.bukkit.block.Block;

/**
 * Uses a pressure block as trigger (wood or stone)
 * 
 * @author Sebastian Fahnenschreiber
 */
public class PressurePlateRedstoneTrigger extends ResettingRedstoneTrigger {
	
	/**
	 * @param pressurePlate the plate to be used as trigger
	 */
	public PressurePlateRedstoneTrigger(final Block pressurePlate) {
		super("pressure_plate", pressurePlate);
	}
	
	/** to be used for persistance */
	public PressurePlateRedstoneTrigger() {}
}
