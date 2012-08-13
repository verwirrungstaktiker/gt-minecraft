package gt.general.logic.response;

import gt.general.logic.TriggerEvent;
import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.exceptions.PersistanceException;

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
	public PersistanceMap dump() {
		PersistanceMap map = new PersistanceMap();
		
		map.put(KEY_LOCATION, teleportLocation);
		return map;
	}

	@Override
	public void setup(PersistanceMap values, World world)
			throws PersistanceException {
		teleportLocation = values.getLocation(KEY_LOCATION, world);
	}
	
	

}
