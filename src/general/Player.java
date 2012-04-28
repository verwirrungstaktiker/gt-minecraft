package general;

import java.awt.Point; //temporary until clear what kind of coordinates are needed
import java.util.Vector;


public class Player extends Agent{


	protected int maxStamina;
	protected int currentStamina;
	protected Team team;
	public Inventory inventory;	
	public Vector<Aura> auras;
	
	public Player(Point position, Point orientation, float defaultSpeed) {
		super(position, orientation, defaultSpeed);
	}	

	
	public int getMaxStamina() {
		return maxStamina;
	}

	public void setMaxStamina(int stamina) {
		this.maxStamina = stamina;
	}
	

	public int getCurrentStamina() {
		return maxStamina;
	}

	public void setCurrentStamina(int stamina) {
		if(stamina <= this.maxStamina) {
			this.currentStamina = stamina;
		}
	}


	public void setTeam(Team team) {
		this.team = team;
	}
	
	
}
