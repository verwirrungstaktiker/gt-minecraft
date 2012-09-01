package gt.plugin.helloworld;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import gt.general.GameManager;
import gt.general.character.HeroManager;
import gt.general.character.TeamManager;
import gt.general.ingameDisplay.ScoreBoard;
import gt.general.world.InstantiatingWorldManager;
import gt.general.world.WorldInstance;
import gt.general.world.WorldManager;
import gt.lastgnome.scoring.Highscore;
import gt.plugin.helloworld.command.StartGameCommandExecutor;
import gt.plugin.helloworld.command.TeamCommandExecutor;
import gt.plugin.meta.Hello;
import gt.plugin.meta.MultiListener;
import gt.plugin.meta.PlayerCommandExecutor;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
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
	
	private static final Map<String,Highscore> HIGHSCORES = newHashMap();
	private static final Collection<String> AVAILABLE_LEVELS = newHashSet();
	
	static {
		AVAILABLE_LEVELS.add("lastgnome");
	}
		
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
		setupLevelTemplates();
	}
	
	/**
	 * setup the levels that are instantiated
	 */
	private void setupLevelTemplates() {
		File worldsFolder = Hello.getPlugin().getServer().getWorldContainer();
		
		for(String levelName : AVAILABLE_LEVELS) {
			
			File levelFolder = new File(worldsFolder, levelName);
			if(levelFolder.exists()) {
				
				setupHighscore(levelName);
				setupScoreBoards(levelName);
				
			} else {
				Bukkit.getLogger().log(Level.WARNING, levelName + " seems not existant");
			}
		}
	}
	
	/** */
	public void onDisable() {
		for(Highscore h : HIGHSCORES.values()) {
			h.saveScores();
		}
	}
	
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
		
		getCommand("instances").setExecutor(new CommandExecutor() {
			
			@Override
			public boolean onCommand(final CommandSender sender, final Command arg1, final String arg2, final String[] arg3) {
				sender.sendMessage("open instances:");
					
				for(WorldInstance world : worldManager.getOpenWorlds()) {
					sender.sendMessage("* " + world.getName());
				}
				return true;
			}
		});
		
		
		getCommand("spectate").setExecutor(new PlayerCommandExecutor() {
			
			@Override
			public boolean onPlayerCommand(final Player player, final Command cmd, final String label, final String[] args) {
				
				if(args[0] == null) {
					player.sendMessage(ChatColor.RED + "usage: /specatate [instance]");
					return true;
				}
				
				for(WorldInstance world : worldManager.getOpenWorlds()) {
					
					if(world.getName().equalsIgnoreCase(args[0])) {
						
						player.sendMessage("specating: " + args[0]);
						
						Location spawn = world.getSpawn().getRespawnLocation().add(0.0, 3.0, 0.0);
						
						player.setGameMode(GameMode.CREATIVE);
						player.teleport(spawn);
						player.setFlying(true);
						
						return true;
					}
				}
				
				player.sendMessage("this instance doesnt exist");
				
				return false;
			}
		});
		


	}

	/**
	 * load the highscores for a level
	 * @param levelName name of the level
	 */
	private void setupHighscore(final String levelName) {
		HIGHSCORES.put(levelName, new Highscore(levelName));
	}

	/**
	 * load the highscore boards
	 * @param levelName name of the level
	 */
	private void setupScoreBoards(final String levelName) {
		
		List<Location> anchors = newArrayList();
		anchors.add(new Location(worldManager.getInitialWorld(), 3, 87, 59, 90, 0));
		anchors.add(new Location(worldManager.getInitialWorld(), 3, 80, 59, 90, 0));
		anchors.add(new Location(worldManager.getInitialWorld(), 3, 73, 59, 90, 0));
		
		ScoreBoard sb = new ScoreBoard(HIGHSCORES.get(levelName), anchors);
		
		Hello.getIngameDisplayManager().add(sb);
		
	}
	
	/**
	 * @param levelName name of the level
	 * @return the highscores for a level
	 */
	public static Highscore getHighscore(final String levelName) {
		return HIGHSCORES.get(levelName);
	}
}
