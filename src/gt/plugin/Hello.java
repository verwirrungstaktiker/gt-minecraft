package gt.plugin;

import gt.general.world.WorldManager;

import org.bukkit.plugin.java.JavaPlugin;

public class Hello {

	// TODO this must be revised!
	private static Hello hello;
	private JavaPlugin plugin = null;
	private WorldManager worldManager;
	
	/**
	 * 
	 * @param task
	 * @param ticks
	 * @return
	 */
	public static int ScheduleSyncTask(Runnable task, int initial, int ticks) {
		return hello.plugin.getServer()
		.getScheduler()
		.scheduleSyncRepeatingTask(hello.plugin, task, initial, ticks);	
	}
	
	public static void cancelTask(int id) {
		hello.plugin.getServer().getScheduler().cancelTask(id);
	}
	
	 public static WorldManager getWorldManager() {
		 return hello.worldManager;
	 }
	 
	 public static JavaPlugin getPlugin() {
		 return hello.plugin;
	 }
	 
	 public static void setPlugin (JavaPlugin plugin){
		 hello.plugin = plugin;
	 }
	 
	 protected Hello () {
		 //worldManager = new WorldManager(null);
	 }
	
	
	
	
}
