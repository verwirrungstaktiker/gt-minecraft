package gt.plugin.helloeditor;

import gt.plugin.helloworld.HelloWorld;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Base for the editor plugin
 * 
 * @author Sebastian Fahnenschreiber
 */
public class HelloEditor extends JavaPlugin {
	
	private static JavaPlugin plugin;
	
	private BuildManager bm = new BuildManager();
	
	/**
	 * Initialization of our plugin
	 */
	public void onEnable() {
		HelloEditor.setPlugin(this);
		
		PluginManager pm = getServer().getPluginManager();
		
		
	}
	
	/** */
	public void onDisable() {
	}
	
	public static void registerListener(final Listener listener) {
		getPlugin()
			.getServer()
			.getPluginManager()
			.registerEvents(listener, getPlugin());
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}

	public static void setPlugin(final JavaPlugin plugin) {
		HelloEditor.plugin = plugin;
	}
	
	/**
	 * TODO this should be encapsuled in a extra class
	 */
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final  String label, final String[] args) {
		/*
		 * set trigger input blocks
		 */
		if (isPlayer(sender) && commandEquals(cmd, "input")) {
			
			return true;
		}
		
		if (isPlayer(sender) && commandEquals(cmd, "null")) {
			return true;
		}
		return false;
	}

	/**
	 * @param cmd The Command to be matched.
	 * @param string The case insensitive String to match the Command.
	 * @return true if the Command matches the String
	 */
	private boolean commandEquals(final Command cmd, final String string) {
		return cmd.getName().equalsIgnoreCase(string);
	}

	/**
	 * @param commandSender the commandSender to be tested
	 * @return true if commandSender is a subclass of Player
	 */
	private boolean isPlayer(final CommandSender commandSender) {
		return commandSender instanceof Player;
	}
}
