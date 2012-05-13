package gt.general;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class HeroManager implements Listener, Runnable {

	private static final Map<Player, Hero> heros = new HashMap<Player, Hero>();
	
	private final JavaPlugin plugin;

	public HeroManager(JavaPlugin plugin) {
		this.plugin = plugin;
		
		registerListener(this);
		
		// simulate on each tick (?)
		plugin
			.getServer()
			.getScheduler()
			.scheduleSyncRepeatingTask(plugin, this, 0, 1);
			
	}
	
	@EventHandler
	public void playerLogin(PlayerLoginEvent ple) {
		
		Player player = ple.getPlayer();
		
		Hero hero = new Hero(player);
		registerListener(hero);
		hero.getPlayer().getInventory().setMaxStackSize(1);
		heros.put(player, hero);
	}
	
	/**
	 * @param listener to be registered for all events
	 */
	private void registerListener(Listener listener) {
		plugin.getServer()
			  .getPluginManager()
			  .registerEvents(listener, plugin);
	}

	@Override
	public void run() {
		
		for(Hero hero : heros.values()) {
			hero.applyEffects();
		}
		
	}
	
	public static Hero getHero(Player player) {
		return heros.get(player);
	}
}
