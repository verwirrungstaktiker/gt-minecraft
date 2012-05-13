package gt.plugin.helloworld;

import gt.general.Hero;
import gt.general.HeroManager;
import gt.lastgnome.GnomeItem;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.SpoutManager;


/**
 * Basic Bukkit Plugin to test stuff
 * 
 * @author Roman
 *
 */
public class HelloWorld extends JavaPlugin {
	
	public static GnomeItem gnome;
	private HeroManager heroManager;
	
	private static JavaPlugin plugin;
	
	public void onEnable() {
		HelloWorld.setPlugin(this);
		
		setupGnome();
		
		heroManager = new HeroManager(this);
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(heroManager, this);
		
		getServer().getScheduler().scheduleSyncRepeatingTask(this, heroManager, 0, 10);
	}
	
	/**
	 * instantiate gnome block and precache it's texture
	 */
	private void setupGnome() {
		gnome = new GnomeItem(this);
		SpoutManager.getFileManager().addToPreLoginCache(plugin, "res/textures/gnome_16x16.png");
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}

	private static void setPlugin(JavaPlugin plugin) {
		HelloWorld.plugin = plugin;
	}
	
	
	
}
