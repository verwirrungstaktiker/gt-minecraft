/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic.response;

import gt.general.logic.TriggerEvent;
import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.exceptions.PersistenceException;

import org.bukkit.World;

/**
 * Modifies the speed of all zombies if triggered.
 */
public class ZombieControlResponse extends ZombieResponse {
	
	private double value;
	
	private static final String KEY_VALUE = "value";
	
	/**
	 * constructor
	 */
	public ZombieControlResponse() {
		super("zombie_control");
		value = 0;
	}


	@Override
	public void triggered(final TriggerEvent e) {
		if (e.isActive()) {
			getZombieManager().addSpeedAll(value);
		}		
	}

	@Override
	public void dispose() {
		//Well luckyly nothing to dispose here
	}

	@Override
	public PersistenceMap dump() {
		PersistenceMap map = new PersistenceMap();
		
		map.put(KEY_VALUE, value);
		return map;
	}

	@Override
	public void setup(final PersistenceMap values, final World world) throws PersistenceException {
		value = (Double) values.get(KEY_VALUE);
	}

}
