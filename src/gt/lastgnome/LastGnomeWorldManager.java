package gt.lastgnome;

import gt.general.trigger.TriggerManager;
import gt.general.world.WorldManager;

import org.bukkit.World;

public class LastGnomeWorldManager extends WorldManager {

	/**
	 * @param initialWorld the world where the initial spawn is
	 */
	public LastGnomeWorldManager(final World initialWorld) {
		super(initialWorld);
	}
	
	/**
	 * @param baseName which world to instantiate
	 * @return the instantiated world
	 */
	public LastGnomeWorldInstance instantiateWorld(final String baseName) {

		String newName = findNextInstanceFolder(baseName);
		
		World world = instatiateWorld(baseName, newName);
		LastGnomeWorldInstance lastGnomeWorld = new LastGnomeWorldInstance(world,
																			new TriggerManager(),
																			new GnomeSocketStart(),
																			new GnomeSocketEnd());
		
		lastGnomeWorld.setName(newName);
		
		getOpenWorlds().add(lastGnomeWorld);
		return lastGnomeWorld;
		
	}
}
