package gt.plugin.helloworld;

import gt.general.Game;
import gt.general.Hero;
import gt.general.HeroManager;
import gt.general.Team;
import gt.lastgnome.GnomeItem;
import gt.lastgnome.GnomeSocketStart;
import gt.lastgnome.LastGnomeGame;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.SpoutManager;
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
	@SuppressWarnings("unused")
	private void setupGnome() {
		gnome = new GnomeItem(this);
		gnomeSocketStart = new GnomeSocketStart(this);
		SpoutManager.getFileManager().addToPreLoginCache(plugin, "http://dl.dropbox.com/u/29386658/gt/textures/gnome_socket_16x16.png");
	}

	public static JavaPlugin getPlugin() {
		return plugin;
	}

	public static void setPlugin(JavaPlugin plugin) {
		HelloWorld.plugin = plugin;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final  String label, final String[] args) {
		if (sender instanceof Player && cmd.getName().equalsIgnoreCase("gg")) {
			Player player = (Player) sender;
			player.sendMessage("starting gnome game");
			
			// TODO this should be a factory once we have more than one game mode
			Hero starter = HeroManager.getHero(player);
			Team team = new Team(HeroManager.getAllHeros());
			LastGnomeGame lgg = new LastGnomeGame(team, starter);
			
			getServer().getPluginManager().registerEvents(lgg, this);
			
			runningGames.add(lgg);
			return true;
		}
		else if (cmd.getName().equalsIgnoreCase("socket")) {
			ItemStack gnomeSockets = new SpoutItemStack(HelloWorld.gnomeSocketStart, 2);
			getServer().getPlayer(sender.getName()).getInventory().addItem(gnomeSockets);
			return true;
		}
		return false;
	}
}
