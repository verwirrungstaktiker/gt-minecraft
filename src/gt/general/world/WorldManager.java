package gt.general.world;

import gt.general.util.CopyUtil;
import gt.plugin.helloworld.HelloWorld;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.WorldCreator;

public class WorldManager {

	private final World initialWorld;
	
	private final Set<WorldInstance> openWorlds;

	public WorldManager(World initialWorld) {
		this.initialWorld = initialWorld;

		openWorlds = new HashSet<WorldInstance>();
	}

	public World getInitialWorld() {
		return initialWorld;
	}
	
	
	protected World getWorldByName(String name) {
		return HelloWorld
				.getPlugin()
				.getServer()
				.getWorld(name);
	}
	
	/**
	 * searchs for a Folder with low Index for an Instance 
	 * @param world the World the instance would be made of
	 * @return
	 */
	protected String findnextInstanceFolder(final World world) {
		int i = 0;
		File worldsfolder = world.getWorldFolder().getParentFile();
		File temp;
		do {
			++i;
			temp = new File(worldsfolder,world.getName()+i);
		} while (temp.exists());
		
		return temp.getName();
	}
	
	/**
	 * duplicates a world
	 * @param world the World to be copied
	 * @param name Name for the duplicate
	 * @return the duplicate
	 */
	protected World instatiateWorld (final World world, final String name) {
		File worldsFolder = world.getWorldFolder().getParentFile();
		File newWorld = new File(worldsFolder,name);
		
		CopyUtil.copyDirectory(world.getWorldFolder(), newWorld);
		
		File uid = new File(newWorld,"uid.dat");
		uid.delete();
		
		WorldCreator wc = new WorldCreator(name);
		//wc.copy(world);
		
		return wc.createWorld();		
	}

	public Set<WorldInstance> getOpenWorlds() {
		return openWorlds;
	}
}
