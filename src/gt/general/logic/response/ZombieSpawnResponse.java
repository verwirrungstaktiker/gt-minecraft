package gt.general.logic.response;

import gt.general.character.ZombieManager;
import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.exceptions.PersistanceException;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;

/**
 * Spawns the set number of Zombies if triggered.
 */
public class ZombieSpawnResponse extends ZombieResponse{
	//private ZombieManager zm;
	private Location spawnLocation;
	private int number;
	private double speed;
	private Set<Entity> spawnedEntities;
	
	private static final String KEY_LOCATION = "location";
	private static final String KEY_NUMBER = "number";
	private static final String KEY_SPEED = "speed";
	
	/**
	 * Empty constructor for Serializable
	 */
	public ZombieSpawnResponse() {
		super("zombie");
		this.speed = 1.0;
		spawnedEntities = new HashSet<Entity>();
	}
	
	/**
	 * A Response for spawning Zombies
	 * @param zm ZombieManager to be used
	 * @param spawnLocation Location, where Zombies should be spawned
	 * @param number Number of Zombies to spawn
	 */
	public ZombieSpawnResponse(final ZombieManager zm, final Location spawnLocation, final int number) {
		super("zombie_spawn");
		setZombieManager(zm);
		this.spawnLocation = spawnLocation;
		this.number = number;
		this.speed = 1.0;
		spawnedEntities = new HashSet<Entity>();
	}
	
	/**
	 * A Response for spawning Zombies
	 * @param zm ZombieManager to be used
	 * @param spawnLocation Location where Zombies are spawned
	 * @param number Number of Zombies that are spawned
	 * @param speed Speed of the spawned Zombies
	 */
	public ZombieSpawnResponse(final ZombieManager zm, final Location spawnLocation, final int number, final double speed) {
		super("zombie");
		setZombieManager(zm);
		this.spawnLocation = spawnLocation;
		this.number = number;
		this.speed = speed;
	}
	
	@Override
	public void triggered(final boolean active) {
		if (active) {
			if (getZombieManager() == null) {
				System.out.print("No ZombieManager: Cannot spawn zombies");
			}
			
			for (int i=0;i<number;++i) {
				if (getZombieManager() == null) {
					spawnedEntities.add(spawnLocation.getWorld()
							.spawn(spawnLocation, Chicken.class));					
				} else {
					getZombieManager().spawnZombie(spawnLocation, speed);
				}
			}
		}
	}
	@Override
	public void dispose() {
		//Clear Entities (Spawned Chicken)
		if (getZombieManager() == null) {
			for (Entity e : spawnedEntities) {
				e.remove();
			}	
		}	
	}
		
	@Override
	public PersistanceMap dump() {
		PersistanceMap map = new PersistanceMap();
		
		map.put(KEY_LOCATION, spawnLocation);
		map.put(KEY_NUMBER, number);
		map.put(KEY_SPEED, speed);
		return map;
	}
	@Override
	public Set<Block> getBlocks() {
		Set<Block> blocks = new HashSet<Block>();
		blocks.add(spawnLocation.getBlock());
		return blocks;
	}
	

	@Override
	public void setup(final PersistanceMap values, final World world) throws PersistanceException {
		number = values.get(KEY_NUMBER);
		speed = values.get(KEY_SPEED);
		
		spawnLocation = values.getLocation(KEY_LOCATION, world);		
	}

}
