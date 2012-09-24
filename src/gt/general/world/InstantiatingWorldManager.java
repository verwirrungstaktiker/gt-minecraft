/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.world;

import gt.plugin.meta.Hello;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import com.google.common.io.Files;

public class InstantiatingWorldManager extends WorldManager {
	
	/**
	 * @param worldName name of the world that is instantiated
	 * @return a new Instance of the World with Triggers, metadata, etc.
	 */
	public WorldInstance getWorld(final String worldName) {
		WorldInstance worldInstance = instantiateWorld(worldName);
		
		getInstanceMapping().put(worldInstance.getWorld(),
								 worldInstance);
		
		return worldInstance;
		
	}
	/**
	 * @param baseName which world to instantiate
	 * @return the instantiated world
	 */
	private WorldInstance instantiateWorld(final String baseName) {

		String newName = findNextInstanceFolder(baseName);
		
		World world = instatiateWorld(baseName, newName);
		WorldInstance worldInstance = new WorldInstance(world);
		
		worldInstance.setName(newName);
		worldInstance.setBaseName(baseName);
		
		return worldInstance;
	}
	
	/**
	 * searchs for a Folder with low Index for an Instance 
	 * @param name name of the world that the instance is made of
	 * @return the name of the next free instance name
	 */
	private String findNextInstanceFolder(final String name) {
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
	private World instatiateWorld (final String baseName, final String newName) {
		File worldsFolder = Bukkit.getServer().getWorldContainer();
		
		File baseWorld = new File(worldsFolder, baseName);
		File newWorld = new File(worldsFolder, newName);
		
		copyDirectory(baseWorld, newWorld);
		
		new File(newWorld,"uid.dat").delete();
		
		// experimental - may solve spout crashes
		Hello.deleteDirectory(new File(newWorld, "spout_meta"));
		
		WorldCreator wc = new WorldCreator(newName);		
		return wc.createWorld();		
	}
	
	/**
	 * Copies a directory including content
	 * @param src the directory to be copied
	 * @param dest the directory to copy to
	 */
	private void copyDirectory(final File src, final File dest) {
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
			e.printStackTrace();
		}
	}		


}
