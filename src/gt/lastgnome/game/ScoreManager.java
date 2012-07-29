package gt.lastgnome.game;

import java.util.Set;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import gt.general.Game;
import gt.general.character.Hero;
import gt.plugin.meta.Hello;


public class ScoreManager implements Runnable, Listener{

	private int taskID;
	private boolean active;
	
	private Game game;
	
	private int time;	// *100ms
	private int deaths;
	private int damage;

	/**
	 * construct new ScoreManager
	 */
	public ScoreManager() {
		time = 0;
		deaths = 0;
		damage = 0;
		active = true;
	}
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
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
		System.out.println("### Time: " + time + " Deaths: " + deaths + " Damage taken: " + damage);
	}

	public int getTime() {
		return time;
	}

	public void setTime(final int time) {
		this.time = time;
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
			if(isPartOfGame(player)) {
				deaths++;
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
			
			if(isPartOfGame(player)) {
				damage += event.getDamage();
//				System.out.println("damage : " + damage);
			}
		}
	}
	
	@Override
	public void run() {
		if(active) {
			time++;
		}
	}

	public int getDeaths() {
		return deaths;
	}
	
	public int getDamage() {
		return damage;
	}
	
	/**
	 * @param player bukkit player
	 * @return true if player participates in the current game
	 */
	private boolean isPartOfGame(final Player player) {
		Set<Hero> members = game.getTeam().getPlayers();
		
		for(Hero hero : members) {
			//check if part of the the monitored team			
			if(hero.getPlayer().equals(player)) {
				return true;
			}
		}
		return false;
	}
}
