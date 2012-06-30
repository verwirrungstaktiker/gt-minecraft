package gt.plugin.helloeditor;

import gt.general.GameManager;
import gt.general.Spawn;
import gt.general.character.TeamManager;
import gt.general.world.WorldManager;
import gt.lastgnome.game.EditorLastGnomeGame;
import gt.lastgnome.game.EditorLastGnomeGameBuilder;
import gt.plugin.Hello;
import gt.plugin.helloworld.KeyPressListener;
import gt.plugin.listener.MultiListener;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.inventory.SpoutItemStack;


/**
 * Base for the editor plugin
 * 
 * @author Sebastian Fahnenschreiber
 */
public class HelloEditor extends JavaPlugin implements Listener {

	private GameManager gameManager;
	private EditorLastGnomeGame game;
	
	/**
	 * Initialization of our plugin
	 */


	public void onEnable() {		
		Hello.initialize(this);
		
		EditorLastGnomeGameBuilder builder = new EditorLastGnomeGameBuilder();
		
		gameManager = new GameManager(new WorldManager(), new TeamManager());
		gameManager.startGame(builder, "lastgnome");
		
		game = builder.getEditorGame();

		

		MultiListener.registerListeners(new KeyPressListener());
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
		gameManager.endAllGames();
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
		
		if(isPlayer(sender) && commandEquals(cmd, "teleport")) {
			((Player)sender).teleport(game.getWorldInstance().getSpawnLocation());
			return true;
		}
		
		if(isPlayer(sender) && commandEquals(cmd, "spawn")) {
			
			System.out.println("generate spawn");
			
			ItemStack items = new SpoutItemStack(Spawn.SPAWN_BLOCK, 1);
			getServer().getPlayer(sender.getName()).getInventory().addItem(items);
			
			return true;
		}
		
		if(commandEquals(cmd, "dump")) {
			// TODO ??
			return true;
		}
		if(commandEquals(cmd, "save")) {
			game.save();
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
