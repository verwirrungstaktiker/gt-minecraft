package gt.general;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Creates and manages all heros
 */
public class HeroManager implements Listener, Runnable {
	/**Heros sorted by player*/
	private static final Map<Player, Hero> HEROS = new HashMap<Player, Hero>();
	/**The plugin the manager runs in*/
	private final JavaPlugin plugin;
	/**
	 * Creates a new HeroManager
	 * @param plugin the plugin we run
	 */
	public HeroManager(final JavaPlugin plugin) {
		this.plugin = plugin;

		registerListener(this);

		// simulate on each tick (?)
		plugin
			.getServer()
			.getScheduler()
			.scheduleSyncRepeatingTask(plugin, this, 0, 1);
	}
	
	/**
	 * creates a hero for every player on login
	 * @param ple a PlayerLoginEvent
	 */
	@EventHandler
	public void playerLogin(final PlayerLoginEvent ple) {
		
		Player player = ple.getPlayer();
		
		Hero hero = new Hero(player);
		registerListener(hero);
		hero.getPlayer().getInventory().setMaxStackSize(1);
		HEROS.put(player, hero);
	}

	/**
	 * @param listener to be registered for all events
	 */
	private void registerListener(final Listener listener) {
		plugin.getServer()
			  .getPluginManager()
			  .registerEvents(listener, plugin);
	}

	@Override
	public void run() {
	
		for(Hero hero : HEROS.values()) {
			hero.simulateEffects();
		}

	}


	/**
	 * gets the hero associated with the player
	 * @param player a Minecraft-Player
	 * @return associated hero
	 */
	public static Hero getHero(final Player player) {
		return HEROS.get(player);
	}
	
	
	/**
	 * @return all currently online heros
	 */
	public static Set<Hero> getAllHeros() {
		return new HashSet<Hero>(HEROS.values());
	}
	
}
