package gt.general.character;

import static com.google.common.collect.Maps.*;
import gt.general.gui.HeroGui;

import java.util.Collection;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Creates and manages all heros
 */
public class HeroManager implements Listener, Runnable {
	/**Heros sorted by player*/
	private final Map<String, Hero> heros = newHashMap();
	private final Map<String, Hero> offlineHeros = newHashMap();	
	
	private final InventoryConnector inventoryConnector = new InventoryConnector();
	
	private static final HeroManager instance = new HeroManager();	
	public static HeroManager getInstance() {
		return instance;
	}

	/**
	 * creates a hero for every player on login
	 * @param e a PlayerLoginEvent
	 */
	@EventHandler
	public void playerLogin(final PlayerJoinEvent e) {
		
		Player player = e.getPlayer();
		String playerName = getPlayerName(player);
		
		Hero hero;
		
		if(heros.containsKey(playerName)) {
			hero = heros.get(playerName);
			
		} else if(offlineHeros.containsKey(playerName)) {
			hero = offlineHeros.remove(playerName);
			heros.put(playerName, hero);

			hero.setPlayer(player);
			hero.getGui().reattach();
			notifyGameReconnect(hero);
			
		} else {
			hero = new Hero(player);
			hero.setGui(new HeroGui(hero));
			
			heros.put(playerName, hero);
		}
				
		hero.getPlayer().getInventory().setMaxStackSize(1);		
		hero.addObserver(inventoryConnector);
		
		System.out.println(hero.getPlayer().getName() + " -- " + (hero.getPlayer() == player));
	}
	
	/**
	 * removes hero on player logout
	 * @param e event from Minecraft
	 */
	@EventHandler
	public void playerLogout(final PlayerQuitEvent e) {

		Player player = e.getPlayer();
		String playerName = getPlayerName(player);
		
		Hero hero = heros.remove(playerName);
		offlineHeros.put(playerName, hero);
				
		notifyGameDisconnect(hero);
	}

	private void notifyGameReconnect(final Hero hero) {
		if(hero.inGame()) {
			hero.getGame().reconnectHero(hero);
		}
	}
	
	private void notifyGameDisconnect(final Hero hero) {
		if(hero.inGame()) {
			hero.getGame().disconnectHero(hero);
		}
	}
	
	@Override
	public void run() {
		for (Hero hero : heros.values()) {
			hero.simulateEffects();
		}
	}

	
	public Map<String, Hero> getHeros() {
		return heros;
	}

	private String getPlayerName(final Player player) {
		return player.getName().toLowerCase();
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
		return getInstance().getHeros().get(name.toLowerCase());
		
	}

	/**
	 * @return all currently online heros
	 */
	public static Collection<Hero> getAllHeros() {
		return getInstance().getHeros().values();
	}

}
