package gt.plugin.helloeditor;

import gt.general.trigger.TriggerManager;
import gt.general.trigger.persistance.TriggerManagerPersistance;
import gt.plugin.helloworld.KeyPressListener;
import gt.plugin.listener.MultiListener;

import org.bukkit.ChatColor;
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
	
	private static HelloEditor plugin;
	
	private BuildManager buildManager = new BuildManager();
	
	private TriggerManager triggerManager = new TriggerManager();
	
	/**
	 * Initialization of our plugin
	 */
	public void onEnable() {
		HelloEditor.setPlugin(this);
		
		MultiListener.initialize(this);
		
		/*
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(bm, this);
		*/
		MultiListener.registerListener(buildManager);
		
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

	public static HelloEditor getPlugin() {
		return plugin;
	}

	public static void setPlugin(final HelloEditor plugin) {
		HelloEditor.plugin = plugin;
	}
	
	/**
	 * @return the TriggerManager of this Editor
	 */
	public TriggerManager getTriggerManager() {
		return triggerManager;
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
		
		if(commandEquals(cmd, "test")) {
			
			MultiListener.registerListener(new KeyPressListener());
			
			return true;
		}
		if(commandEquals(cmd, "dump")) {
			//TODO: ??
			return true;
		}
		if(commandEquals(cmd, "toyaml")) {
			System.out.println("\n" + TriggerManagerPersistance.toYaml(triggerManager));
		}
		return false;
	}

	private void printChatHelp(Player player) {
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
