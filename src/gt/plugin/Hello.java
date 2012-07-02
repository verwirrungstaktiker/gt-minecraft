package gt.plugin;

import gt.general.world.WorldManager;
import gt.plugin.listener.MultiListener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Hello {

	private static Hello instance;
	
	private final JavaPlugin plugin;
	private WorldManager worldManager;
	
	/**
	 * Schedules a synchron Task
	 * @param task the task to be scheduled 
	 * @param ticks task is executed every ticks ticks
	 * @return taskId
	 */
	public static int ScheduleSyncTask(Runnable task, int initial, int ticks) {
		return Bukkit
					.getScheduler()
					.scheduleSyncRepeatingTask(Hello.getPlugin(), task, initial, ticks);	
	}
	
	/**
	 * Schedules a asynchron Task
	 * @param task the task to be scheduled 
	 * @param ticks task is executed every ticks ticks
	 * @return taskId
	 */
	public static int ScheduleAsyncTask(Runnable task, int initial, int ticks) {
		return Bukkit
					.getScheduler()
					.scheduleAsyncRepeatingTask(Hello.getPlugin(), task, initial, ticks);	
	}	
	
	public static void cancelTask(int id) {
		Bukkit.getScheduler().cancelTask(id);
	}
	
	
	 public static WorldManager getWorldManager() {
		 if (instance != null) {
			 return instance.worldManager;
		 } else {
			 throw new RuntimeException("Hello must be initialized");
		 }		 
	 }
	 
	 public static JavaPlugin getPlugin() {
		 return Hello.getInstance().plugin;
	 }
	 
	 protected Hello (final JavaPlugin plugin) {
		 this.plugin = plugin;
	 }
	 
	 public static void initialize(final JavaPlugin plugin) {
		 if (instance == null) {
			 
			 instance = new Hello(plugin);
			 MultiListener.initialize(plugin);
			 
		 } else {
			 throw new RuntimeException("Hello already initialized");
		 }
	 }
	 
	 public static Hello getInstance() {
		 if (instance != null) {
			 return instance;
		 } else {
			 throw new RuntimeException("Hello must be initialized");
		 }
	 }
	
	
	
	
}
