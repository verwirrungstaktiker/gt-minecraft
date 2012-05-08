package gt.plugin.helloworld;

import gt.general.Hero;
import gt.general.HeroManager;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Basic Bukkit Plugin to test stuff
 * 
 * @author Roman
 *
 */
public class HelloWorld extends JavaPlugin {
	
	HeroManager heroManager;
	
	public void onEnable() {
		
		heroManager = new HeroManager(this);
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(heroManager, this);
	}
	
}
