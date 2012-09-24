/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general;

import static com.google.common.collect.Maps.newHashMap;
import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.general.character.Team;
import gt.general.logic.TriggerContext;
import gt.general.logic.TriggerManager;
import gt.general.logic.response.Response;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnManager implements Listener {
	
	private final TriggerManager triggerManager;
	
	private final Map<Hero, RespawnPoint> currentRespawns = newHashMap();
	
	public interface RespawnPoint {
		/**
		 * @param respawnManager respawnManager to be registered
		 */
		void registerRespawnManager(final RespawnManager respawnManager);
		 
		/**
		 * @return location of respawn
		 */
		Location getRespawnLocation();
	}
	
	/**
	 * @param team the team to watch
	 * @param triggerManager the running trigger manager
	 * @param spawn the spawn of the level
	 */
	public RespawnManager(final Team team, final TriggerManager triggerManager, final Spawn spawn) {
		this.triggerManager = triggerManager;
		
		for(Hero h : team.getPlayers()) {
			currentRespawns.put(h, spawn);
		}
		
		collectRespawnResponses();
	}
	
	/**
	 * collect all respawn points
	 */
	private void collectRespawnResponses() {
		
		for(TriggerContext context : triggerManager.getTriggerContexts()) {
			
			for(Response response : context.getResponses()) {
				
				if(response instanceof RespawnPoint) {
					((RespawnPoint) response).registerRespawnManager(this);
				}
			}
		}
	}

	/**
	 * register a new respawnpoint for a player
	 * @param player a bukkit player
	 * @param respawnPoint new respawnpoint of a player
	 */
	public void registerRespawnPoint(final Player player, final RespawnPoint respawnPoint) {
		
		Hero hero = HeroManager.getHero(player);
		currentRespawns.put(hero, respawnPoint);
	}
	
	/**
	 * register a new respawnpoint for the whole team
	 * @param respawnPoint the new respawnpoint for the whole team
	 */
	public void registerTeamRespawnPoint(final RespawnPoint respawnPoint) {
		for (Hero h : currentRespawns.keySet()) {
			currentRespawns.put(h, respawnPoint);
		}
	}
	
	/**
	 * handling during respawn
	 * @param event event: a player respawns
	 */
	@EventHandler
	public void onRespawn(final PlayerRespawnEvent event) {
		
		
		Player player = event.getPlayer();		
		Hero hero = HeroManager.getHero(player);
		
		
		if(currentRespawns.containsKey(hero)) {
			event.setRespawnLocation(
					currentRespawns.get(hero).getRespawnLocation());
		}
	}

	/**
	 * clear all respawn points
	 */
	public void dispose() {
		currentRespawns.clear();
	}
}
