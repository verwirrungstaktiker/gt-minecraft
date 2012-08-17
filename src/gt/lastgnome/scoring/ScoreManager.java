package gt.lastgnome.scoring;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import gt.general.Game;
import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.general.character.Team;
import gt.plugin.meta.Hello;


public class ScoreManager implements Runnable, Listener{

	private int taskID;
	private boolean active;
	
	private Game game;

	private Score score;
	/**
	 * construct new ScoreManager
	 */
	public ScoreManager() {
		active = true;
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
		score = new Score(game.getTeam());
	}

	/**
	 * start counting
	 */
	public void startMonitoring() {
		taskID = Hello.scheduleSyncTask(this, 0, 2);
		active = true;
	}
	
	/**
	 * stop counting
	 */
	public void stopMonitoring() {
		Hello.cancelScheduledTask(taskID);
		active = false;
		System.out.println("### Time: " + getTime() + " Deaths: " + getDeaths() + " Damage taken: " + getDeaths());
	}

	public int getTime() {
		return score.getTime();
	}

	public void setTime(final int time) {
		score.setTime(time);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(final boolean active) {
		this.active = active;
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
			if(isPartOfGame(hero)) {
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
			if(isPartOfGame(hero)) {
				score.addDamage(hero, event.getDamage());
			}
		}
	}
	
	@Override
	public void run() {
		if(active) {
			score.setTime(score.getTime()+1);
		}
	}

	public int getDeaths() {
		return score.getTotalDeaths();
	}
	
	public int getDamage() {
		return score.getTotalDamage();
	}
	
	public int getDeaths(Hero hero) {
		return score.getDeaths(hero);
	}
	
	public int getDamage(Hero hero) {
		return score.getDamage(hero);
	}
	
	public Score getScore() {
		return score;
	}
	
	/**
	 * @param Hero one of our heros
	 * @return true if Hero participates in the current game
	 */
	private boolean isPartOfGame(final Hero hero) {
		Team team = hero.getTeam();
		
		//check if part of the the monitored team			
		if(game.getTeam().equals(team)) {
			return true;
		} else {
			return false;
		}
	}
}
