package gt.general;

import java.awt.Point; //temporary until clear what kind of coordinates are needed
import java.util.Vector;


public class Player extends Agent{

	protected Team team;
	public Inventory inventory;	
	public Vector<Aura> auras;
	
	public Player(Point position, Point orientation, float defaultSpeed) {
		super(position, orientation, defaultSpeed);
	}	


	public void setTeam(Team team) {
		this.team = team;
	}
	
	
}
