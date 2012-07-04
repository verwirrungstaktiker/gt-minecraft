package gt.plugin.helloeditor;

import gt.general.GameManager;
import gt.general.character.TeamManager;
import gt.general.world.WorldManager;
import gt.lastgnome.game.EditorLastGnomeGame;
import gt.lastgnome.game.EditorLastGnomeGameBuilder;
import gt.plugin.helloworld.KeyPressListener;
import gt.plugin.meta.Hello;
import gt.plugin.meta.MultiListener;
import gt.plugin.meta.PlayerCommandExecutor;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
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
		
		setupCommands();
	}
	
	/** */
	public void onDisable() {
		gameManager.endAllGames();
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
	 * configures CommandExecutors
	 */
	private void setupCommands() {
		getCommand("helpme").setExecutor(new PlayerCommandExecutor() {
			@Override
			public boolean onPlayerCommand(final Player player, final Command cmd, final String label, final String[] args) {
				printChatHelp(player);
				return true;
			}
		});
		
		getCommand("teleport").setExecutor(new PlayerCommandExecutor() {
			@Override
			public boolean onPlayerCommand(final Player player, final Command cmd, final String label, final String[] args) {
				player.teleport(game.getWorldInstance().getSpawnLocation());
				return true;
			}
		});
		
		getCommand("blocks").setExecutor(new PlayerCommandExecutor() {
			
			@Override
			public boolean onPlayerCommand(final Player player, final Command cmd, final String label, final String[] args) {
				Hello.giveCustomBlocks(player);
				return false;
			}
		});
		
		getCommand("save").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
				game.save();
				System.out.println("saved.");
				return true;
			}
		});
	}
}
