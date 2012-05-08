package gt.general;

import gt.general.aura.Aura;

import java.util.Vector;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;


public class Hero extends Character implements Listener{
	
	
	/** unbuffed default value **/
	public static final double DEFAULT_HERO_SPEED = 100;
	/** unbuffed default value, range 0-100 **/
	public static final double DEFAULT_HERO_STAMINA = 100;
	
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

	@Override
	public Location getLocation() {
		return player.getLocation();
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
