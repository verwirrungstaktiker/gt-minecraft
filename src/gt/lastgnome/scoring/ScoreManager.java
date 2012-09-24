/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.lastgnome.scoring;

import gt.general.Game;
import gt.general.character.Hero;
import gt.general.character.HeroManager;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;


public class ScoreManager implements Listener{

	private boolean active;
	private long monitoringStarted;
	
	private final Game game;

	private final Score score;
	
	/**
	 * construct new ScoreManager
	 * @param game the corresponding game
	 */
	public ScoreManager(final Game game) {
		this.game = game;
		active = true;
		
		score = new Score(game.getTeam());
	}

	/**
	 * start counting
	 */
	public void startMonitoring() {		
		monitoringStarted = System.currentTimeMillis();
		active = true;
	}
	
	/**
	 * stop counting
	 */
	public void stopMonitoring() {
		if(active) {
			score.addTime(System.currentTimeMillis() - monitoringStarted);
			active = false;
		}
	}

	/**
	 * counts deaths on the team
	 * @param event player dies
	 */
	@EventHandler
	public void countPlayerDeaths(final PlayerDeathEvent event) {
		if(active) {
			Player player = event.getEntity();
			Hero hero = HeroManager.getHero(player);
			if(game.isPlayedBy(hero)) {
				score.addDeath(hero);
			}
		}
	}
	
	/**
	 * count damage taken in the team
	 * @param event entity takes damage
	 */
	@EventHandler
	public void countPlayerDamage(final EntityDamageByEntityEvent event) {
		if(active && event.getEntityType() == EntityType.PLAYER) {
			Player player = (Player) event.getEntity();
			Hero hero = HeroManager.getHero(player);
			if(game.isPlayedBy(hero)) {
				score.addDamage(hero, event.getDamage());
			}
		}
	}
	
	/**
	 * @return a HighscoreEntry for the current game
	 */
	public HighscoreEntry toHighscoreEntry() {
		
		HighscoreEntry hse = score.toHighscoreEntry();
		
		for(Hero h : game.getTeam().getPlayers()) {
			hse.addPlayer(h.getPlayer().getName());
		}
		
		return hse;
	}
	
}
