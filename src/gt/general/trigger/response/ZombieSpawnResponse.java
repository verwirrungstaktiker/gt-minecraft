package gt.general.trigger.response;

import gt.general.character.ZombieManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class ZombieSpawnResponse extends Response{
	private ZombieManager zm;
	private Location spawnLocation;
	private int number;
	private double speed;
	
	/**
	 * A Response for spawning Zombies
	 * @param zm ZombieManager to be used
	 * @param spawnLocation Location, where Zombies should be spawned
	 * @param number Number of Zombies to spawn
	 */
	public ZombieSpawnResponse(ZombieManager zm,Location spawnLocation, int number) {
		this.zm = zm;
		this.spawnLocation = spawnLocation;
		this.number = number;
		this.speed = 1.0;
	}
	
	/**
	 * A Response for spawning Zombies
	 * @param zm ZombieManager to be used
	 * @param spawnLocation Location, where Zombies should be spawned
	 * @param number Number of Zombies to spawn
	 * @param speed Speed of the spawned Zombies
	 */
	public ZombieSpawnResponse(ZombieManager zm,Location spawnLocation, int number, double speed) {
		this.zm = zm;
		this.spawnLocation = spawnLocation;
		this.number = number;
		this.speed = speed;
	}
	
	@Override
	public void triggered(boolean active) {
		if (active) {
			for (int i=0;i<number;++i) {
				zm.spawnZombie(spawnLocation, speed);
			}
		}
	}
	@Override
	public void dispose() {
		//Nothing to do, maybe clearing Zombies?
		//zm.clearZombies();
	}
	@Override
	public Map<String, Object> dump() {
		Map<String, Object> map = new HashMap<String,Object>();
		
		map.putAll(coordinatesFromPoint(spawnLocation.getBlock()));
		map.put("number", number);
		map.put("speed", speed);
		return map;
	}
	@Override
	public Set<Block> getBlocks() {
		return null;
	}
	@Override
	public void setup(Map<String, Object> values, World world) {
		number = (Integer) values.get("number");
		speed = (Double) values.get("speed");
		
		spawnLocation = blockFromCoordinates(values, world).getLocation();		
	}
	

}