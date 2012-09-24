/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.plugin.helloeditor;

import gt.general.GameManager;
import gt.general.logic.persistence.exceptions.PersistenceException;
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
 * The Editor Plugin
 * 
 * @author Sebastian Fahnenschreiber
 */
public class HelloEditor extends JavaPlugin implements Listener {

	private GameManager gameManager;
	private EditorLastGnomeGameBuilder gameBuilder;
	private EditorLastGnomeGame game;
	
	/**
	 * Initialization of our plugin
	 */
	public void onEnable() {		
		Hello.initialize(this);
		
		gameBuilder = new EditorLastGnomeGameBuilder();
		
		gameManager = new GameManager(new WorldManager());
		gameManager.startGame(gameBuilder, "lastgnome");
		
		game = gameBuilder.getEditorGame();

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
		
		getCommand("save").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
				game.save();
				System.out.println("saved.");
				return true;
			}
		});
		
		getCommand("load").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
				
				System.out.println("reload");
				try {
					gameBuilder.reload();
				} catch (PersistenceException e) {
					e.printStackTrace();
				}
				
				return true;
			}
		});
		
	}
}
