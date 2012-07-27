package gt.general.logic.response;

import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.exceptions.PersistanceException;

import org.bukkit.World;

public class ZombieClearResponse extends ZombieResponse {

	public ZombieClearResponse() {
		super("zombie_clear");
	}

	@Override
	public void triggered(boolean active) {
		if (active) {
			getZombieManager().clearZombies();
		}
	}

	@Override
	public void dispose() {
	
	}

	@Override
	public PersistanceMap dump() {
		PersistanceMap map = new PersistanceMap();
		return map;
	}

	@Override
	public void setup(PersistanceMap values, World world)
			throws PersistanceException {
		
	}

}