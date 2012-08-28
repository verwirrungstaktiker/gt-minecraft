package gt.general.logic.response;

import gt.general.logic.TriggerEvent;
import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.exceptions.PersistenceException;

import org.bukkit.World;

public class ZombieClearResponse extends ZombieResponse {

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
