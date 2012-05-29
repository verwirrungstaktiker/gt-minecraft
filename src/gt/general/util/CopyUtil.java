package gt.general.util;

import gt.plugin.helloworld.HelloWorld;

import java.io.File;
import java.io.IOException;

import org.bukkit.World;
import org.bukkit.WorldCreator;

import com.google.common.io.Files;

public class CopyUtil {
	
	/**
	 * Deletes a directory including content
	 * @param dir directory to be deleted
	 */
	public static void deleteDirectory(File dir) {

			for (File f : dir.listFiles()) {
				if (f.isDirectory()) {
					deleteDirectory(f);
				} else {
					f.delete();
				}
			dir.delete();	
			}

	}

	/**
	 * Copies a directory including content
	 * @param src the directory to be copied
	 * @param dest the directory to copy to
	 */
	public static void copyDirectory(File src, File dest) {
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
	 * duplicates a world
	 * @param world the World to be copied
	 * @param name Name for the duplicate
	 * @return the duplicate
	 */
	public static World copyWorld(World world,String name) {
		File worldsFolder = world.getWorldFolder().getParentFile();
		File newWorld = new File(worldsFolder,name);
		
		copyDirectory(world.getWorldFolder(), newWorld);
		
		File uid = new File(newWorld,"uid.dat");
		uid.delete();
		
		WorldCreator wc = new WorldCreator(name);
		//wc.copy(world);
		
		return wc.createWorld();		
	}
	
	/**
	 * duplicates a world
	 * @param world Name of the World to be copied
	 * @param name Name for the duplicate
	 * @return the duplicate
	 */
	public static World copyWorld(String world,String name) {
		World w = HelloWorld.getPlugin().getServer().getWorld(world);
		return copyWorld(w,name);
	}
	
	/**
	 * searchs for a Folder with low Index for an Instance 
	 * @param world the World the instance would be made of
	 * @return
	 */
	public static String findnextInstanceFolder(World world) {
		int i = 0;
		File worldsfolder = world.getWorldFolder().getParentFile();
		File temp;
		do {
			++i;
			temp = new File(worldsfolder,world.getName()+i);
		} while (temp.exists());
		
		return temp.getName();
	}
	
}
