package gt.general;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class HeroManager implements Listener {

	private final Set<Hero> heros;
	
	private final JavaPlugin plugin;

	public HeroManager(JavaPlugin plugin) {
		this.plugin = plugin;
		
		heros = new HashSet<Hero>();
		registerListener(this);
	}
	
	@EventHandler
	public void playerLogin(PlayerLoginEvent ple) {
		
		Hero hero = new Hero(ple.getPlayer());
		registerListener(hero);
		hero.getPlayer().getInventory().setMaxStackSize(1);
		heros.add(hero);
	}
	
	/**
	 * @param listener to be registered for all events
	 */
	private void registerListener(Listener listener) {
		plugin.getServer()
			  .getPluginManager()
			  .registerEvents(listener, plugin);
	}
	
}
