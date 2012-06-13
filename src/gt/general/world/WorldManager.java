package gt.general.world;

import gt.plugin.helloworld.HelloWorld;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import com.google.common.io.Files;

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
	 * @return all currnently open worlds
	 */
	public Set<WorldInstance> getOpenWorlds() {
		return openWorlds;
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
	 * @param world the World the instance would be made of
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
	 * @param world the World to be copied
	 * @param name Name for the duplicate
	 * @return the duplicate
	 */
	protected World instatiateWorld (final String baseName, final String newName) {
		File worldsFolder = Bukkit.getServer().getWorldContainer();
		
		File baseWorld = new File(worldsFolder, baseName);
		File newWorld = new File(worldsFolder, newName);
		
		copyDirectory(baseWorld, newWorld);
		
		File uid = new File(newWorld,"uid.dat");
		uid.delete();
		
		WorldCreator wc = new WorldCreator(newName);
		//wc.copy(world);
		
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
	

	
	public void disposeWorldInstance(WorldInstance worldInstance) {
		World w = worldInstance.getWorld();
		final File f = w.getWorldFolder();

		Bukkit.getServer().unloadWorld(w, false);
		
		for (Chunk c : w.getLoadedChunks()) {
			//w.unloadChunk(c);
			System.out.println("unload chunk: " + c.getX() + " | " + c.getZ());
			System.out.println("result: " + c.unload(false, true));
		}

		worldInstance.dispose();
		openWorlds.remove(worldInstance);
		
		Thread r = new Thread() {
			@Override
			public void run() {				
				Bukkit.broadcastMessage("del folder");
				deleteDirectory(f);
				Bukkit.broadcastMessage("del folder finished");
			}
		};
		
		r.start();


	}
	
	/**
	 * Deletes a directory including content
	 * @param dir directory to be deleted
	 */
	public void deleteDirectory(final File dir) {
		
		System.out.println(dir.getName());
		
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {		
				deleteDirectory(f);
			} else {
				if(!f.delete()) {
					System.out.println("error on: " + f.getName());
				}
			}
		}
		
		dir.delete();
	}
}
