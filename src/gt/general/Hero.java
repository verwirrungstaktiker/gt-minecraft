package gt.general;

import gt.general.aura.Aura;

import java.awt.Point; //temporary until clear what kind of coordinates are needed
import java.util.Vector;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;


public class Hero extends Character{

	private static final float DEFAULT_HERO_SPEED = 100;
	
	protected Team team;
	public Inventory inventory;	
	public Vector<Aura> auras;
	
	final private Player player;
	
	public Hero(Player player) {
		super(DEFAULT_HERO_SPEED);
		
		team = Team.NOTEAM;
		this.player = player;
		inventory = new Inventory();
	}
	
	/**
	 * @return the wrapped Player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @return the team
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * @param team the team to set
	 */
	public void setTeam(Team team) {
		this.team = team;
	}
	
}
