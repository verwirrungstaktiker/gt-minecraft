package gt.plugin.helloworld;

import org.bukkit.plugin.java.JavaPlugin;


/**
 * Basic Bukkit Plugin to test stuff
 * 
 * @author Roman
 *
 */
public class HelloWorld extends JavaPlugin {
	
		public void onEnable() {
			getServer().getPluginManager().registerEvents(new BlockListener(), this);
		}
	
}
