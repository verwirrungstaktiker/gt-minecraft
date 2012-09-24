/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic.response;

import gt.general.character.ZombieManager;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.block.Block;

public abstract class ZombieResponse extends Response {

	private ZombieManager zm;

	/**
	 * don't delete
	 * @param labelPrefix name prefix
	 */
	public ZombieResponse(final String labelPrefix) {
		super(labelPrefix);
	}

	@Override
	public Set<Block> getBlocks() {
		return new HashSet<Block>();
	}
	
	/**
	 * @param zm the new zombieManager
	 */
	public void setZombieManager(final ZombieManager zm) {
		this.zm = zm;		
	}
	
	/**
	 * @return the zombieManager
	 */
	public ZombieManager getZombieManager() {
		return zm;
	}

}
