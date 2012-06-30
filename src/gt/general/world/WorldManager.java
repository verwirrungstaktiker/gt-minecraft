package gt.general.world;

import static com.google.common.collect.Maps.*;

import gt.general.Spawn;
import gt.general.trigger.TriggerManager;

import java.util.Collection;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldManager {

	private final World initialWorld;
		
	private final Map<World, WorldInstance> instanceMapping;

	/**
	 * @param initialWorld the base world, where the initial spawn is
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

	protected Map<World, WorldInstance> getInstanceMapping() {
		return instanceMapping;
	}
	
	/**
	 * @return all currnently open worlds
	 */
	public Collection<WorldInstance> getOpenWorlds() {
		return instanceMapping.values();
	}	

	public void setupWorldInstance(final WorldInstance worldInstance, final TriggerManager triggerManager) {
		
		triggerManager.setup("trigger.yml", worldInstance);
		worldInstance.setTriggerManager(triggerManager);
		
		Spawn spawn = new Spawn();
		spawn.setup("spawn.yml", worldInstance);
		
		worldInstance.setSpawn(spawn);
	}
	
	/** 
	 * @param worldInstance will be disposed 
	 */
	public void disposeWorldInstance(final WorldInstance worldInstance) {
		instanceMapping.remove(worldInstance.getWorld());

		worldInstance.getTriggerManager().dispose();
		worldInstance.getSpawn().dispose();
	}
	


	public WorldInstance getWorld(String worldName) {
		
		World world = Bukkit.getWorld(worldName);
		
		if( instanceMapping.containsKey(world) ) {
			return instanceMapping.get(world);
		} else {
			
			WorldInstance worldInstance = new WorldInstance(world);
			
			instanceMapping.put(world, worldInstance);
			return worldInstance;
		}
	}
}
