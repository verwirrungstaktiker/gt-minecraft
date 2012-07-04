package gt.plugin.meta;

import gt.general.world.ObservableCustomBlock;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.inventory.SpoutItemStack;

/**
 * Core singleton - must be initialized with Hello.initialize(JavaPlugin)
 */
public final class Hello {
	
	private static ObservableCustomBlock invisibleBlock;

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
			
			invisibleBlock = new ObservableCustomBlock("invisibleBlock", "https://dl.dropbox.com/u/29386658/gt/textures/invisible.png", 16);
			invisibleBlock.setOpaque(true);

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
	 * Schedules a synchron Task
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
	 * Schedules a asynchron Task
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
		player.getInventory().setItemInHand(new SpoutItemStack(invisibleBlock));
	}
	 
	/**
	 * @return the currently running plugin
	 */
	public static JavaPlugin getPlugin() {
		return Hello.getInstance().plugin;
	}

}
