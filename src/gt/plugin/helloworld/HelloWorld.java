package gt.plugin.helloworld;

import gt.general.GameManager;
import gt.general.character.HeroManager;
import gt.general.character.TeamManager;
import gt.general.world.InstantiatingWorldManager;
import gt.general.world.WorldManager;
import gt.plugin.helloworld.command.StartGameCommandExecutor;
import gt.plugin.helloworld.command.TeamCommandExecutor;
import gt.plugin.meta.Hello;
import gt.plugin.meta.MultiListener;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Basic Bukkit Plugin to test stuff
 *
 * @author Roman
 *
 */
public class HelloWorld extends JavaPlugin {
	
	private HeroManager heroManager;
	private GameManager gameManager;
	
	private TeamManager teamManager;
	private WorldManager worldManager;
		
	/**
	 * Initialization of our plugin
	 */
	public void onEnable() {
		Hello.initialize(this);

		heroManager = new HeroManager(this);
		teamManager = new TeamManager();
		worldManager = new InstantiatingWorldManager();
		
		
		MultiListener.registerListeners(new KeyPressListener(),
										new BlockListener(),
										new PlayerListener(),
										heroManager);
		
		Hello.scheduleSyncTask(heroManager, 0, 10);
		
		gameManager = new GameManager(worldManager, teamManager);
		
		
		setupCommands();
	}
	
	/** */
	public void onDisable() { }
	
	/**
	 * initializes the commands associated with hello world
	 */
	private void setupCommands() {
		getCommand("team").setExecutor(new TeamCommandExecutor(teamManager));
		getCommand("gg").setExecutor(new StartGameCommandExecutor(teamManager, gameManager));
		
		getCommand("end").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] arg3) {
				System.out.println("ending games");
				gameManager.endAllGames();
				return true;
			}
		});
	}
}
