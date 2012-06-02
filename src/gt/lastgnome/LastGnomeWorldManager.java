package gt.lastgnome;

import gt.general.world.WorldManager;

import org.bukkit.World;

public class LastGnomeWorldManager extends WorldManager {

	public LastGnomeWorldManager(World initialWorld) {
		super(initialWorld);
	}
	
	public LastGnomeWorldInstance instantiateWorld(String baseName) {

		World baseWorld = getWorldByName(baseName);
		String newName = findnextInstanceFolder(baseWorld);
		
		LastGnomeWorldInstance lastGnomeWorld = new LastGnomeWorldInstance();
			
		lastGnomeWorld.setWorld(instatiateWorld(baseWorld, newName));
		lastGnomeWorld.setName(newName);
		
		getOpenWorlds().add(lastGnomeWorld);
		return lastGnomeWorld;
		
	}
}
