package gt.plugin.helloworld;

import gt.general.Game;
import gt.general.Hero;
import gt.general.HeroManager;
import gt.general.Team;
import gt.general.trigger.TriggerManager;
import gt.general.util.CopyUtil;
import gt.lastgnome.GnomeItem;
import gt.lastgnome.GnomeSocketEnd;
import gt.lastgnome.GnomeSocketStart;
import gt.lastgnome.LastGnomeGame;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;


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

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new BlockListener(), this);
		pm.registerEvents(heroManager, this);

		getServer().getScheduler().scheduleSyncRepeatingTask(this, heroManager, 0, 10);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, tm, 0, 10);
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
	
	/**
	 * instantiate gnome block and precache it's texture
	 */
	@SuppressWarnings("unused")
	private void setupGnome() {
		gnome = new GnomeItem(this);
		gnomeSocketStart = new GnomeSocketStart(this);
		gnomeSocketEnd = new GnomeSocketEnd(this);
		SpoutManager.getFileManager().addToPreLoginCache(plugin, "http://dl.dropbox.com/u/29386658/gt/textures/gnome_socket_start_16x16.png");
		SpoutManager.getFileManager().addToPreLoginCache(plugin, "http://dl.dropbox.com/u/29386658/gt/textures/gnome_socket_end_16x16.png");
		SpoutManager.getFileManager().addToPreLoginCache(plugin, "http://dl.dropbox.com/u/29386658/gt/textures/gnome2_16x16.png");
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
			World world = getServer().getWorld("world");
			LastGnomeGame lgg = new LastGnomeGame(team, world, starter);
			
			getServer().getPluginManager().registerEvents(lgg, this);
			
			runningGames.add(lgg);
			return true;
		} else if (cmd.getName().equalsIgnoreCase("end")) {
			
			System.out.println("ending games");
			
			for(Game g : runningGames) {
				g.dispose();
			}
			
			runningGames.clear();
			
		} else if (cmd.getName().equalsIgnoreCase("gc")) {
			System.gc();
			return true;
		} else if (sender instanceof Player && cmd.getName().equalsIgnoreCase("test")) {
			System.out.println("TEST");
		}
		else if (cmd.getName().equalsIgnoreCase("socket")) {
			ItemStack items = new SpoutItemStack(HelloWorld.gnomeSocketStart, 1);
			getServer().getPlayer(sender.getName()).getInventory().addItem(items);
			
			items = new SpoutItemStack(HelloWorld.gnomeSocketEnd, 1);
			getServer().getPlayer(sender.getName()).getInventory().addItem(items);
			return true;
		}
		return false;
	}
}
