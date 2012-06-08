package gt.general.world;

import gt.general.util.CopyUtil;
import gt.plugin.helloworld.HelloWorld;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class WorldManager {

	private final World initialWorld;
	
	private final Set<WorldInstance> openWorlds;

	/**
	 * @param initialWorld the base world, where the initial spawn is
	 */
	public WorldManager(final World initialWorld) {
		this.initialWorld = initialWorld;

		openWorlds = new HashSet<WorldInstance>();
	}

	/**
	 * @return the world containing the initial spawn
	 */
	public World getInitialWorld() {
		return initialWorld;
	}
	
	/**
	 * @param name the name of the world
	 * @return the minecraft representation of the world
	 */
	protected World getWorldByName(final String name) {
		return HelloWorld
				.getPlugin()
				.getServer()
				.getWorld(name);
	}
	
	/**
	 * searchs for a Folder with low Index for an Instance 
	 * @param name name of the world that the instance is made of
	 * @return the name of the next free instance name
	 */
	protected String findNextInstanceFolder(final String name) {
		int i = 0;
		File worldsfolder = Bukkit.getServer().getWorldContainer();
		File temp;
		do {
			++i;
			temp = new File(worldsfolder,name+"_"+i);
		} while (temp.exists());
		
		return temp.getName();
	}
	
	/**
	 * duplicates a world
	 * @param baseName name of the world to be copied
	 * @param newName Name for the duplicate
	 * @return the duplicate
	 */
	protected World instatiateWorld (final String baseName, final String newName) {
		File worldsFolder = Bukkit.getServer().getWorldContainer();
		
		File baseWorld = new File(worldsFolder, baseName);
		File newWorld = new File(worldsFolder, newName);
		
		CopyUtil.copyDirectory(baseWorld, newWorld);
		
		File uid = new File(newWorld,"uid.dat");
		uid.delete();
		
		WorldCreator wc = new WorldCreator(newName);
		//wc.copy(world);
		
		return wc.createWorld();		
	}

	/**
	 * @return all currnently open worlds
	 */
	public Set<WorldInstance> getOpenWorlds() {
		return openWorlds;
	}
}
