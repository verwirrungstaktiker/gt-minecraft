package gt.plugin.helloworld;

import gt.general.Game;
import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.general.character.Team;
import gt.general.character.TeamManager;
import gt.general.trigger.TriggerManager;
import gt.general.util.CopyUtil;
import gt.lastgnome.GnomeItem;
import gt.lastgnome.GnomeSocketEnd;
import gt.lastgnome.GnomeSocketStart;
import gt.lastgnome.LastGnomeGameManager;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.inventory.SpoutItemStack;


/**
 * Basic Bukkit Plugin to test stuff
 *
 * @author Roman
 *
 */
public class HelloWorld extends JavaPlugin {

	public static GnomeItem gnome;
	public static GnomeSocketStart gnomeSocketStart;
	public static GnomeSocketEnd gnomeSocketEnd;
	
	private HeroManager heroManager;
	private static JavaPlugin plugin;
	private Set<Game> runningGames;
	private static TriggerManager tm;
	
	private LastGnomeGameManager lastGnomeGameManager;
	
	private TeamManager teamManager;
	
	public static TriggerManager getTM() {
		return tm;
	}

	

	
	/**
	 * Initialization of our plugin
	 */
	public void onEnable() {
		HelloWorld.setPlugin(this);

		setupGnome();

		runningGames = new HashSet<Game>();
		heroManager = new HeroManager(this,runningGames);
		tm = new TriggerManager();
		teamManager = new TeamManager();

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(new PlayerListener(), this);
		pm.registerEvents(new KeyPressListener(), this);
		pm.registerEvents(heroManager, this);

		getServer().getScheduler().scheduleSyncRepeatingTask(this, heroManager, 0, 10);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, tm, 0, 10);
		
		lastGnomeGameManager = new LastGnomeGameManager(getServer().getWorld("world"));
	}
	
	/**
	 * 
	 */
	public void onDisable() {
		for (World world : getServer().getWorlds()) {
			if (world.getName().equals("world")) {
				continue;
			}
			
			CopyUtil.deleteDirectory(world.getWorldFolder());
		}
	}
	
	public static void registerListener(Listener listener) {
		getPlugin()
			.getServer()
			.getPluginManager()
			.registerEvents(listener, getPlugin());
	}

	public Set<Game> getRunningGames() {
		return runningGames;
	}
	
	/**
	 * instantiate gnome block and precache it's texture
	 */
	private void setupGnome() {
		gnome = new GnomeItem(this);
		gnomeSocketStart = new GnomeSocketStart(this);
		gnomeSocketEnd = new GnomeSocketEnd(this);
//		SpoutManager.getFileManager().addToPreLoginCache(plugin, "http://dl.dropbox.com/u/29386658/gt/textures/gnome_socket_start_16x16.png");
//		SpoutManager.getFileManager().addToPreLoginCache(plugin, "http://dl.dropbox.com/u/29386658/gt/textures/gnome_socket_end_16x16.png");
//		SpoutManager.getFileManager().addToPreLoginCache(plugin, "http://dl.dropbox.com/u/29386658/gt/textures/gnome2_16x16.png");
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}

	public static void setPlugin(JavaPlugin plugin) {
		HelloWorld.plugin = plugin;
	}
	
	/**
	 * TODO this should be encapsuled in a extra class
	 */
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final  String label, final String[] args) {
		
		/*
		 * Gnome game
		 */
		if (isPlayer(sender) && commandEquals(cmd, "gg")) {
			startGnomeGame(sender);
			return true;
			
		} else if (commandEquals(cmd, "end")) {
			endAllGames();
			return true;
		
		/*
		 * Team management
		 */
		} else if (isPlayer(sender) && commandEquals(cmd, "team")) {
			dispatchTeamCommand(sender, args);
			return true;
			
		/*
		 * Debug / Testing
		 */
		} else if (commandEquals(cmd, "gc")) {
			System.gc();
			return true;
			
		} else if (commandEquals(cmd, "test")) {
			System.out.println("TEST");
			return true;
			
		} else if (isPlayer(sender) && commandEquals(cmd, "socket")) {
			giveSocketsToPlayer(sender);
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

	/**
	 * Handles the <tt>/gg</tt> command.
	 * Starts a new LastGnomeGame for the Team of the sender.
	 * 
	 * @param sender the starter of the last gnome game
	 */
	private void startGnomeGame(final CommandSender sender) {
		Player player = (Player) sender;
		
		// TODO this should be a factory once we have more than one game mode
		Hero starter = HeroManager.getHero(player);
		
		// build the team
		Team team = starter.getTeam();
		if(team == null) {
			team = teamManager.initiateTeam(starter);
			
			for(Hero hero : HeroManager.getAllHeros()) {
				try {
					teamManager.addHeroToTeam(team, hero);
				} catch (TeamManager.TeamJoinException tje) {
					sender.sendMessage(tje.getMessage());
				}
			}
		}
		
		getServer().broadcastMessage("starting gnome game: " + starter.getPlayer().getName());
		lastGnomeGameManager.startGame(team, starter, "world_nether");
	}

	/**
	 * Handles the <tt>/end</tt> command.
	 * Ends all currently running games.
	 */
	private void endAllGames() {
		System.out.println("ending games");
		lastGnomeGameManager.endAllGames();
		
		for(Team team : teamManager.getTeams()) {
			teamManager.disband(team);
		}
	}
	
	/**
	 * Handles the <tt>/team</tt> command.
	 * /team disband Deletes the team of the sender
	 * /team [nick] ... Creates a new team and invites the attached Heros
	 * 
	 * @param sender who typed /team
	 * @param args arguments attached to the command
	 */
	private void dispatchTeamCommand(final CommandSender sender, final String[] args) {
		Hero invoker = HeroManager.getHero((Player) sender);
		
		// disband the team
		if (args.length == 1 && args[0].equalsIgnoreCase("disband")) {
			
			System.out.println("disbanded a team");
			teamManager.disband(invoker.getTeam());
			return;
		}
		
		// create a new team
		if (invoker.getTeam() == Team.NOTEAM) {
			teamManager.initiateTeam(invoker);
		}
		
		// now the invoker must have a team
		Team team = invoker.getTeam();
		for (String name : args) {
			
			try {
				teamManager.addHeroToTeamByName(team, name);
			} catch (TeamManager.TeamJoinException tje) {
				sender.sendMessage(tje.getMessage());
			}
			
		}
		
		// XXX DEBUG
		System.out.println(invoker.getTeam().toString());
	}

	/**
	 * XXX DEBUG
	 * 
	 * @param sender 
	 */
	private void giveSocketsToPlayer(final CommandSender sender) {
		ItemStack items = new SpoutItemStack(HelloWorld.gnomeSocketStart, 1);
		getServer().getPlayer(sender.getName()).getInventory().addItem(items);
		
		items = new SpoutItemStack(HelloWorld.gnomeSocketEnd, 1);
		getServer().getPlayer(sender.getName()).getInventory().addItem(items);
	}
	
}
