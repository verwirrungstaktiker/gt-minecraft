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
}
