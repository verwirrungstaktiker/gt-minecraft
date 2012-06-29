package gt.general.world;

import static com.google.common.collect.Maps.*;
import static com.google.common.collect.Sets.*;
import gt.plugin.Hello;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import com.google.common.io.Files;

public class WorldManager {

	private final World initialWorld;
	
	private final Set<WorldInstance> openWorlds;
	
	private final Map<World, WorldInstance> instanceMapping;

	/**
	 * @param initialWorld the base world, where the initial spawn is
	 */
	public WorldManager(final World initialWorld) {
		this.initialWorld = initialWorld;

		openWorlds = newHashSet();
		instanceMapping = newHashMap();
	}

	public WorldInstance getWorldInstance (final World world) {
		return instanceMapping.get(world);
	}
	
	/**
	 * @return the world containing the initial spawn
	 */
	public World getInitialWorld() {
		return initialWorld;
	}

	/**
	 * @return all currnently open worlds
	 */
	public Set<WorldInstance> getOpenWorlds() {
		return openWorlds;
	}
	
	/**
	 * @param baseName which world to instantiate
	 * @return the instantiated world
	 */
	public WorldInstance instantiateWorld(final String baseName) {

		String newName = findNextInstanceFolder(baseName);
		
		World world = instatiateWorld(baseName, newName);
		WorldInstance worldInstance = new WorldInstance(world);
		
		worldInstance.setName(newName);
		
		getOpenWorlds().add(worldInstance);
		
		instanceMapping.put(world, worldInstance);
		
		return worldInstance;
		
	}
	
	/**
	 * @param name the name of the world
	 * @return the minecraft representation of the world
	 */
	protected World getWorldByName(final String name) {
		return Hello.getPlugin()
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
		
		copyDirectory(baseWorld, newWorld);
		
		new File(newWorld,"uid.dat").delete();
		
		// experimental - may solve spout crashes
		deleteDirectory(new File(newWorld, "spout_meta"));
		
		WorldCreator wc = new WorldCreator(newName);		
		return wc.createWorld();		
	}
	
	/**
	 * Copies a directory including content
	 * @param src the directory to be copied
	 * @param dest the directory to copy to
	 */
	public void copyDirectory(final File src, final File dest) {
		try {
			dest.mkdir();
			for (File f : src.listFiles()) {
				if (f.isDirectory()) {
					copyDirectory(f, new File(dest,f.getName()));
				} else {
					Files.copy(f, new File(dest,f.getName()));
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}		
	

	/** 
	 * @param worldInstance will be disposed 
	 */
	public void disposeWorldInstance(final WorldInstance worldInstance) {
		World w = worldInstance.getWorld();

		Bukkit.getServer().unloadWorld(w, false);

		worldInstance.dispose();
		openWorlds.remove(worldInstance);
	}
	
	/**
	 * Deletes a directory including content
	 * @param dir directory to be deleted
	 */
	public void deleteDirectory(final File dir) {
				
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {		
				deleteDirectory(f);
			} else {
				f.delete();
			}
		}
		
		dir.delete();
	}

	public WorldInstance getWorld(String worldName) {
		// TODO subclass for simple wrapping
		return instantiateWorld(worldName);
	}
}
