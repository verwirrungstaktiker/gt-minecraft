/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic.response;

import gt.general.logic.TriggerEvent;
import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.exceptions.PersistenceException;

import org.bukkit.World;

public class ZombieClearResponse extends ZombieResponse {

	/**
	 * don't delete this anonymous constructor
	 */
	public ZombieClearResponse() {
		super("zombie_clear");
	}

	@Override
	public void triggered(final TriggerEvent e) {
		if (e.isActive()) {
			getZombieManager().clearZombies();
		}
	}

	@Override
	public void dispose() {
	
	}

	@Override
	public PersistenceMap dump() {
		PersistenceMap map = new PersistenceMap();
		return map;
	}

	@Override
	public void setup(final PersistenceMap values, final World world)
			throws PersistenceException {
		
	}

}
