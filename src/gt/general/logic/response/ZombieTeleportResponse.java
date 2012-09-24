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

import org.bukkit.Location;
import org.bukkit.World;

public class ZombieTeleportResponse extends ZombieResponse{

	private static final String KEY_LOCATION = "location";
	
	private Location teleportLocation;
	
	/**
	 * don't delete this anonymous constructor
	 */
	public ZombieTeleportResponse() {
		super("zombie_teleport");
	}

	@Override
	public void triggered(final TriggerEvent triggerEvent) {
		getZombieManager().teleportZombies(teleportLocation);
	}

	@Override
	public void dispose() {
	}

	@Override
	public PersistenceMap dump() {
		PersistenceMap map = new PersistenceMap();
		
		map.put(KEY_LOCATION, teleportLocation);
		return map;
	}

	@Override
	public void setup(final PersistenceMap values, final World world)
			throws PersistenceException {
		teleportLocation = values.getLocation(KEY_LOCATION, world);
	}
	
	

}
