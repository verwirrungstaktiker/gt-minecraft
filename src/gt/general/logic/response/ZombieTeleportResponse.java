package gt.general.logic.response;

import gt.general.logic.TriggerEvent;
import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.exceptions.PersistenceException;

import org.bukkit.Location;
import org.bukkit.World;

public class ZombieTeleportResponse extends ZombieResponse{

	private static final String KEY_LOCATION = "location";
	
	private Location teleportLocation;
	
	public ZombieTeleportResponse() {
		super("zombie_teleport");
	}

	@Override
	public void triggered(TriggerEvent triggerEvent) {
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
	public void setup(PersistenceMap values, World world)
			throws PersistenceException {
		teleportLocation = values.getLocation(KEY_LOCATION, world);
	}
	
	

}
