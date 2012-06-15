package gt.plugin.helloeditor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Base for the editor plugin
 * 
 * @author Sebastian Fahnenschreiber
 */
public class HelloEditor extends JavaPlugin {
	
	private static JavaPlugin plugin;
	
	/**
	 * Initialization of our plugin
	 */
	public void onEnable() {

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
		 * Gnome game
		 */
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
