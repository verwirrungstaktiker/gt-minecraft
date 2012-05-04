package gt.general;

import gt.general.aura.Aura;

import java.awt.Point; //temporary until clear what kind of coordinates are needed
import java.util.Vector;

import org.bukkit.Location;


public class Player extends Character{

	private static final float DEFAULT_SPEED = 100;
	
	protected Team team;
	public Inventory inventory;	
	public Vector<Aura> auras;
	
	
	public Player(float defaultSpeed) {
		super(defaultSpeed);
		
		team = Team.NOTEAM;
		inventory = new Inventory();
	}
	
	public Player() {
		this(DEFAULT_SPEED);
	}


	public void setTeam(Team team) {
		this.team = team;
	}
	
	
}
