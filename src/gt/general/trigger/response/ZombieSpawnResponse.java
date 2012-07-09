package gt.general.trigger.response;

import gt.general.character.ZombieManager;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Spawns the set number of Zombies if triggered.
 */
public class ZombieSpawnResponse extends Response{
	private ZombieManager zm;
	private Location spawnLocation;
	private int number;
	private double speed;
	
	/**
	 * Empty constructor for Serializable
	 */
	public ZombieSpawnResponse() {
		super();
		this.speed = 1.0;
	}
	
	/**
	 * A Response for spawning Zombies
	 * @param zm ZombieManager to be used
	 * @param spawnLocation Location, where Zombies should be spawned
	 * @param number Number of Zombies to spawn
	 */
	public ZombieSpawnResponse(final ZombieManager zm, final Location spawnLocation, final int number) {
		this.zm = zm;
		this.spawnLocation = spawnLocation;
		this.number = number;
		this.speed = 1.0;
	}
	
	/**
	 * A Response for spawning Zombies
	 * @param zm ZombieManager to be used
	 * @param spawnLocation Location where Zombies are spawned
	 * @param number Number of Zombies that are spawned
	 * @param speed Speed of the spawned Zombies
	 */
	public ZombieSpawnResponse(final ZombieManager zm, final Location spawnLocation, final int number, final double speed) {
		this.zm = zm;
		this.spawnLocation = spawnLocation;
		this.number = number;
		this.speed = speed;
	}
	
	@Override
	public void triggered(final boolean active) {
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
		
		map.putAll(coordinatesFromPoint(spawnLocation));
		map.put("number", number);
		map.put("speed", speed);
		return map;
	}
	@Override
	public Set<Block> getBlocks() {
		return new HashSet<Block>();
	}
	@Override
	public void setup(final Map<String, Object> values, final World world) {
		number = (Integer) values.get("number");
		speed = (Double) values.get("speed");
		
		spawnLocation = locationFromCoordinates(values, world);		
	}
	
	/**
	 * @param zm a ZombieManager
	 */
	public void setZombieManager(final ZombieManager zm) {
		this.zm = zm;
	}

	/**
	 * only spawn a flame effect at the spawn location as it has no blocks
	 */
	@Override
	public void highlight() {
		spawnLocation.getWorld().playEffect(spawnLocation, Effect.MOBSPAWNER_FLAMES, 25);
	}

}
