package gt.lastgnome;

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
		
		LastGnomeWorldInstance lastGnomeWorld = new LastGnomeWorldInstance();
			
		lastGnomeWorld.setWorld(instatiateWorld(baseName, newName));
		lastGnomeWorld.setName(newName);
		
		getOpenWorlds().add(lastGnomeWorld);
		return lastGnomeWorld;
		
	}
}
