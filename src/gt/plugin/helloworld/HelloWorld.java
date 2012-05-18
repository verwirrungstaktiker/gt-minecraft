package gt.plugin.helloworld;

import gt.general.Game;
import gt.general.Hero;
import gt.general.HeroManager;
import gt.general.Team;
import gt.lastgnome.GnomeItem;
import gt.lastgnome.LastGnomeGame;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.SpoutManager;


/**
 * Basic Bukkit Plugin to test stuff
 *
 * @author Roman
 *
 */
public class HelloWorld extends JavaPlugin {

	public static GnomeItem gnome;
	private HeroManager heroManager;

	private static JavaPlugin plugin;

	private Set<Game> runningGames;
	
	/**
	 * Initialization of our plugin
	 */
	public void onEnable() {
		HelloWorld.setPlugin(this);

		setupGnome();

		heroManager = new HeroManager(this);
		runningGames = new HashSet<Game>();

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(heroManager, this);

		getServer().getScheduler().scheduleSyncRepeatingTask(this, heroManager, 0, 10);
	}

	/**
	 * instantiate gnome block and precache it's texture
	 */
	private void setupGnome() {
		gnome = new GnomeItem(this);
		SpoutManager.getFileManager().addToPreLoginCache(plugin, "res/textures/gnome_16x16.png");
	}
	
	

	public static JavaPlugin getPlugin() {
		return plugin;
	}

	private static void setPlugin(JavaPlugin plugin) {
		HelloWorld.plugin = plugin;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final  String label, final String[] args) {
		if (sender instanceof Player && cmd.getName().equalsIgnoreCase("gnome_game")) {
			Player player = (Player) sender;
			player.sendMessage("starting gnome game");
			
			Hero starter = HeroManager.getHero(player);
			Team team = new Team(HeroManager.getAllHeros());
			runningGames.add(new LastGnomeGame(team, starter));
			
			return true;
		}
		return false;
	}
}
