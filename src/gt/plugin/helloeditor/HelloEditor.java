package gt.plugin.helloeditor;

import gt.general.trigger.TriggerManager;
import gt.lastgnome.LastGnomeWorldInstance;
import gt.plugin.listener.MultiListener;

import org.bukkit.ChatColor;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
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
public class HelloEditor extends JavaPlugin implements Listener {
	
	private static HelloEditor plugin;
	
	private TriggerManager triggerManager;
	private BuildManager buildManager;
	private PlayerManager playerManager;

	private LastGnomeWorldInstance worldInstance;
	
	/**
	 * Initialization of our plugin
	 */
	public void onEnable() {
		HelloEditor.setPlugin(this);
		MultiListener.initialize(this);

		triggerManager = new TriggerManager();
		playerManager = new PlayerManager(triggerManager);
		buildManager = new BuildManager(playerManager);
		
		
		WorldCreator wc = new WorldCreator("lastgnome");
		wc.environment(Environment.NORMAL);
		worldInstance = new LastGnomeWorldInstance(wc.createWorld(), triggerManager);		
		
		MultiListener.registerListeners(playerManager);
		MultiListener.registerListeners(buildManager);
		
		printInformation();	
	}
	
	/**
	 * help
	 */
	private void printInformation() {
		System.out.println(
				"\n  ********************** \n" +
				"  Welcome to Build Mode \n" +
				"   [F6]   -- Toggle Trigger Context Mode \n" +
				"   [F7]   -- Toggle Player Trigger State (trigger/response)\n" +
				"   [F9]   -- Toggle Context Input Function (and/or)\n" +
				"   [F12]   -- Cancel Trigger Context Mode \n" +
				"   /helpme -- show this information \n" +
				"  **********************");
	}

	/** */
	public void onDisable() {

	}
	
	/**
	 * register a Listener directly with bukkit
	 * 
	 * @param listener The Listener to be registered
	 */
	public static void registerListener(final Listener listener) {
		getPlugin()
			.getServer()
			.getPluginManager()
			.registerEvents(listener, getPlugin());
	}

	/**
	 * @return the Editor
	 */
	public static HelloEditor getPlugin() {
		return plugin;
	}

	/**
	 * @param plugin the Editor to be set
	 */
	public static void setPlugin(final HelloEditor plugin) {
		HelloEditor.plugin = plugin;
	}

	/*
	 * TODO this should be encapsuled in a extra class
	 */
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final  String label, final String[] args) {
		/*
		 * set trigger input blocks
		 */
		if(isPlayer(sender) && commandEquals(cmd, "helpme")) {
			printChatHelp((Player) sender);
			return true;
		}
		
		if(isPlayer(sender) && commandEquals(cmd, "tp")) {
			((Player)sender).teleport(worldInstance.getSpawnLocation());
			return true;
		}
		
		if(commandEquals(cmd, "dump")) {
			// TODO ??
			return true;
		}
		if(commandEquals(cmd, "save")) {
			worldInstance.saveTriggerManager();
			System.out.println("saved.");
			return true;
		}
		if(commandEquals(cmd, "load")) {
			// TODO
			return true;
		}
		return false;
	}

	/**
	 * @param player the player which needs some help
	 */
	private void printChatHelp(final Player player) {
		player.sendMessage(
				ChatColor.YELLOW + 
				"* [F6]Toggle Trigger Context \n" +
				"* [F7]Toggle Trigger State \n" +
				"* [F9]Toggle Context Mode \n" +
				"*[F12]Cancel Trigger Context");
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
