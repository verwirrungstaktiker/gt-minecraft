/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.character;

import static com.google.common.collect.Maps.newHashMap;
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
	
	private static final HeroManager INSTANCE = new HeroManager();	
	
	/**
	 * @return an Instance of this Class
	 */
	public static HeroManager getInstance() {
		return INSTANCE;
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

	/**
	 * reconnection handling
	 * @param hero a player 
	 */
	private void notifyGameReconnect(final Hero hero) {
		if(hero.inGame()) {
			hero.getGame().reconnectHero(hero);
		}
	}
	
	/**
	 * disconnect handling
	 * @param hero a player
	 */
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

	/**
	 * @return all heroes
	 */
	public Map<String, Hero> getHeros() {
		return heros;
	}

	/**
	 * @param player a bukkit player
	 * @return the name of the bukkit player
	 */
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
