package gt.general.world;

import static com.google.common.collect.Maps.*;

import java.util.Collection;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class WorldManager {

	private final World initialWorld;
		
	private final Map<World, WorldInstance> instanceMapping;

	/**
	 * generates a new WorldManager
	 */
	public WorldManager() {
		initialWorld = Bukkit.getWorld("world");
		instanceMapping = newHashMap();
	}
	
	/**
	 * @return the world containing the initial spawn
	 */
	public World getInitialWorld() {
		return initialWorld;
	}

	/**
	 * @return the internal mapping of active world instances
	 */
	protected Map<World, WorldInstance> getInstanceMapping() {
		return instanceMapping;
	}
	
	/**
	 * @return all currently open worlds
	 */
	public Collection<WorldInstance> getOpenWorlds() {
		return instanceMapping.values();
	}	

	/**
	 * @param worldName the name of the world instance to get
	 * @return the world instance
	 */
	public WorldInstance getWorld(final String worldName) {
		
		World world = Bukkit.getWorld(worldName);
		
		// if world is not loaded yet
		if (world == null) {
			WorldCreator worldCreator = new WorldCreator(worldName);
			world = worldCreator.createWorld();
		}
		
		if( instanceMapping.containsKey(world) ) {
			return instanceMapping.get(world);
		} else {
			
			WorldInstance worldInstance = new WorldInstance(world);
			worldInstance.setName(worldName);
			
			instanceMapping.put(world, worldInstance);
			return worldInstance;
		}
	}
}
