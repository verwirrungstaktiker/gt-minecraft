package gt.plugin.meta;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Core singleton - must be initialized with Hello.initialize(JavaPlugin)
 */
public final class Hello {
	
	private static Hello instance;
	private final JavaPlugin plugin;

	/**
	 * @param plugin the currently running plugin
	 */
	private Hello(final JavaPlugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * @param plugin the currently running plugin
	 */
	public static void initialize(final JavaPlugin plugin) {
		if (instance == null) {

			instance = new Hello(plugin);
			MultiListener.initialize(plugin);
			CustomBlockType.instantiate();
			
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
	 * Adds the Set of CustomBlocks to the player inventory
	 * TODO don't forget to add other CustomBlocks later
	 * 
	 * @param player a bukkit player
	 */
	public static void giveCustomBlocks(final Player player) {
		player.getInventory().setItemInHand(CustomBlockType.INVISIBLE_BLOCK.getItemStack());
	}
	 
	/**
	 * @return the currently running plugin
	 */
	public static JavaPlugin getPlugin() {
		return Hello.getInstance().plugin;
	}
	
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

}
