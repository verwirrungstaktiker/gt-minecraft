package gt.plugin.helloworld;

import gt.general.Hero;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Basic Bukkit Plugin to test stuff
 * 
 * @author Roman
 *
 */
public class HelloWorld extends JavaPlugin {
	
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		
		pm.registerEvents(new BlockListener(), this);
	}
	
}
