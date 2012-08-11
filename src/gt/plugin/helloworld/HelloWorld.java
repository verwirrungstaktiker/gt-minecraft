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
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Basic Bukkit Plugin to test stuff
 *
 * @author Roman
 *
 */
public class HelloWorld extends JavaPlugin {
	private GameManager gameManager;
	
	private TeamManager teamManager;
	private WorldManager worldManager;
		
	/**
	 * Initialization of our plugin
	 */
	public void onEnable() {
		Hello.initialize(this);

		teamManager = new TeamManager();
		worldManager = new InstantiatingWorldManager();
		
		HeroManager heroManager = HeroManager.getInstance();
		
		MultiListener.registerListeners(new KeyPressListener(),
										new LevelSecurity(),
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
		
		getCommand("fly").setExecutor(new CommandExecutor() {
			@Override
			public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] arg3) {
				if (arg0 instanceof Player) {
					Player player = (Player) arg0;
					player.setAllowFlight(true);
					// Lift-off ;)
					player.teleport(player.getLocation().add(0.0, 0.1, 0.0));
					player.setFlying(true);
					
					return true;
				}
				return false;
			}
		});		
		
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
