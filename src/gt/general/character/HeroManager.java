package gt.general.character;

import gt.general.Game;
import gt.general.gui.HeroGui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Creates and manages all heros
 */
public class HeroManager implements Listener, Runnable {
	/**Heros sorted by player*/
	private static final Map<String, Hero> HEROS = new HashMap<String, Hero>();
	/**The plugin the manager runs in*/
	private final JavaPlugin plugin;
	
	private final InventoryConnector inventoryConnector;
	
	private final Set<Game> runningGames;
	/**
	 * Creates a new HeroManager
	 * @param plugin the plugin we run
	 * @param runningGames all currently running games
	 */
	public HeroManager(final JavaPlugin plugin, final Set<Game>runningGames) {
		this.plugin = plugin;
		this.runningGames = runningGames;
		registerListener(this);

		inventoryConnector = new InventoryConnector();
		
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
		
		// TODO redo this
		for (Game game : runningGames) {
			Hero hero = game.getDisconnectedHero(player);
			if (hero != null) {
				registerListener(hero);
				HEROS.put(player.getName().toLowerCase(), hero);
				game.restoreHero(hero);
				return;
			}
		}
		
		Hero hero = new Hero(player);
		hero.getPlayer().getInventory().setMaxStackSize(1);
		hero.setGui(new HeroGui(hero));
		
		hero.addObserver(inventoryConnector);
		
		registerListener(hero);
		HEROS.put(player.getName().toLowerCase(), hero);
	}
	
	/**
	 * removes hero on player logout
	 * @param pqe event from Minecraft
	 */
	@EventHandler
	public void playerLogout(final PlayerQuitEvent pqe) {
		Hero hero = HEROS.get(pqe.getPlayer().getName().toLowerCase());
		Team team = hero.getTeam();
		
		// TODO redo this
		if (team != null) {
			Game game = hero.getTeam().getGame();
			if (game != null) {
				game.disconnectHero(hero);
			}
			
		}
		
		
		HEROS.remove(pqe.getPlayer());
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

		for (Hero hero : HEROS.values()) {
			hero.simulateEffects();
		}

	}


	/**
	 * gets the hero associated with the player
	 * @param player a Minecraft-Player
	 * @return associated hero
	 */
	public static Hero getHero(final Player player) {
		return getHero(player.getName());
	}
	
	/**
	 * @param name The name of the Hero to find
	 * @return the Hero
	 */
	public static Hero getHero(final String name) {
		return HEROS.get(name.toLowerCase());
		
	}

	/**
	 * @return all currently online heros
	 */
	public static Set<Hero> getAllHeros() {
		return new HashSet<Hero>(HEROS.values());
	}

}
