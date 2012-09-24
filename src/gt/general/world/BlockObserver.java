/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.world;

import gt.general.world.ObservableCustomBlock.BlockEvent;

public interface BlockObserver {

	/**
	 * @param blockEvent Player interacts with a ObservableCustomBlock
	 */
	void onBlockEvent(BlockEvent blockEvent);

}
