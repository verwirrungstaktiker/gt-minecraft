package gt.general;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;

public class HeroManager {

	Set<Hero> players;
	
	public HeroManager() {
		players = new HashSet<Hero>();
	}
	
	@EventHandler
	public void playerLogin(PlayerLoginEvent ple) {
		
		Hero h = new Hero( ple.getPlayer());
		players.add(h);
	}
	
}
