/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.plugin.meta;

import gt.general.ingameDisplay.IngameDisplayManager;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Core singleton - must be initialized with Hello.initialize(JavaPlugin)
 */
public final class Hello {
	
	private static Hello instance;
	private final JavaPlugin plugin;

	private final IngameDisplayManager ingameDisplayManager;
	
	/**
	 * @param plugin the currently running plugin
	 */
	private Hello(final JavaPlugin plugin) {
		this.plugin = plugin;
		this.ingameDisplayManager = new IngameDisplayManager();
		
	}

	/**
	 * @param plugin the currently running plugin
	 */
	public static void initialize(final JavaPlugin plugin) {
		if (instance == null) {

			instance = new Hello(plugin);
			MultiListener.initialize(plugin);
			CustomBlockType.instantiate();
			
			scheduleAsyncTask(instance.ingameDisplayManager, 1, IngameDisplayManager.REFRESH_RATE);

			
			//Well, let's clean up on start
			File worldsFolder = Bukkit.getServer().getWorldContainer();
			for (File f : worldsFolder.listFiles()) {
				if (f.isDirectory() && f.getName().contains("_")) {
					deleteDirectory(f);
				}
			}
			
		} else {
			throw new RuntimeException("Hello already initialized");
		}
	}

	/**
	 * @return the instance of the singleton
	 */
	public static Hello getInstance() {
		if (instance != null) {
			return instance;
		} else {
			throw new RuntimeException("Hello must be initialized");
		}
	}
	
	/**
	 * Schedules an synchronous Task
	 * 
	 * shortcut for BukkitScheduler#scheduleSyncRepeatingTask(JavaPlugin, Runnable, int, int)
	 * 
	 * @param task the task to be scheduled
	 * @param initial number of ticks before the first schedule
	 * @param ticks task is executed every ticks ticks
	 * @return taskId
	 */
	public static int scheduleSyncTask(final Runnable task, final int initial, final int ticks) {
		return Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(), task, initial, ticks);
	}

	/**
	 * Schedules an asynchronous Task
	 * 
	 * shortcut for BukkitScheduler#scheduleAsyncRepeatingTask(JavaPlugin, Runnable, int, int)
	 * 
	 * @param task the task to be scheduled
	 * @param initial number of ticks before the first schedule
	 * @param ticks task is executed every ticks ticks
	 * @return taskId
	 */
	public static int scheduleAsyncTask(final Runnable task, final int initial, final int ticks) {
		return Bukkit.getScheduler().scheduleAsyncRepeatingTask(getPlugin(), task, initial, ticks);
	}

	/**
	 * Schedules an asynchronous
	 * @param task the task to be executed
	 * @param delay duration in ticks till task is executed
	 * @return taskId
	 */
	public static int scheduleOneTimeTask(final Runnable task, final int delay) {
		return Bukkit.getScheduler().scheduleAsyncDelayedTask(getPlugin(), task, delay);
	}
	
	/**
	 * @param id the id of the task to be cancelled
	 */
	public static void cancelScheduledTask(final int id) {
		Bukkit.getScheduler().cancelTask(id);
	}
	 
	/**
	 * @return the currently running plugin
	 */
	public static JavaPlugin getPlugin() {
		return getInstance().plugin;
	}
	
	/**
	 * @param event the event to be fired by bukkit
	 */
	public static void callEvent(final Event event) {
		Bukkit
			.getServer()
			.getPluginManager()
			.callEvent(event);
	}

	/**
	 * Deletes a directory including content
	 * @param dir directory to be deleted
	 */
	public static void deleteDirectory(final File dir) {
		
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {		
				deleteDirectory(f);
			} else {
				f.delete();
			}
		}
		
		dir.delete();
	}
	
	/**
	 * @return the currently running Ingame Display manager (particle style text in the game)
	 */
	public static IngameDisplayManager getIngameDisplayManager() {
		return getInstance().ingameDisplayManager;
	}

}
