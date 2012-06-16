package gt.plugin.helloworld;

import gt.general.Game;
import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.general.character.Team;
import gt.general.character.TeamManager;
import gt.general.trigger.TriggerManager;
import gt.lastgnome.LastGnomeGameManager;
import gt.plugin.listener.MultiListener;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.command.Command;
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
	
	private HeroManager heroManager;
	private static HelloWorld plugin; //!singleton
	private Set<Game> runningGames;
	private static TriggerManager triggerManager;
	
	private LastGnomeGameManager lastGnomeGameManager;
	
	private TeamManager teamManager;
		
	/**
	 * Initialization of our plugin
	 */
	public void onEnable() {
		HelloWorld.setPlugin(this);

		runningGames = new HashSet<Game>();
		heroManager = new HeroManager(this,runningGames);
		triggerManager = new TriggerManager();
		teamManager = new TeamManager();

		
		MultiListener.initialize(this);
		
		MultiListener.registerListeners(new KeyPressListener(),
										new BlockListener(),
										// new CommandListener(), XXX seems to be missing?
										new PlayerListener());
		
		MultiListener.registerListener(heroManager);		

		getServer().getScheduler().scheduleSyncRepeatingTask(this, heroManager, 0, 10);
		
		lastGnomeGameManager = new LastGnomeGameManager(getServer().getWorld("world"), teamManager);
	}
	
	/** */
	public void onDisable() { }
	
	/**
	 * @return all currently running games
	 */
	public Set<Game> getRunningGames() {
		return runningGames;
	}

	/**
	 * @return the HelloWorld plugin
	 */
	public static HelloWorld getPlugin() {
		return plugin;
	}

	/**
	 * @return The TriggerManager associated to this Plugin
	 */
	public static TriggerManager getTriggerManager() {
		return triggerManager;
	}

	/**
	 * @param plugin the plugin to set
	 */
	public static void setPlugin(final HelloWorld plugin) {
		HelloWorld.plugin = plugin;
	}
	
	/*
	 * TODO this should be encapsuled in a extra class
	 */
	@SuppressWarnings("javadoc")
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
		lastGnomeGameManager.startGame(team, "lastgnome");
	}

	/**
	 * Handles the <tt>/end</tt> command.
	 * Ends all currently running games.
	 */
	private void endAllGames() {
		System.out.println("ending games");
		lastGnomeGameManager.endAllGames();
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
		
		sender.sendMessage("this command is currently not availible");
		
		/*
		ItemStack items = new SpoutItemStack(HelloWorld.gnomeSocketStart, 1);
		getServer().getPlayer(sender.getName()).getInventory().addItem(items);
		
		items = new SpoutItemStack(HelloWorld.gnomeSocketEnd, 1);
		getServer().getPlayer(sender.getName()).getInventory().addItem(items);
		*/
	}
	
}
