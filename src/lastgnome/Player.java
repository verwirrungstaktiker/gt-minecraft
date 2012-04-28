package lastgnome;

import java.awt.Point; //temporary until clear what kind of coordinates are needed
import java.util.Vector;


public class Player extends Agent{


	protected int endurance;
	protected Team team;
	public Inventory inventory;	
	public Vector<Aura> aurae;
	
	public Player(Point position) {
		super(position);
	}	

	/**
	 * method to take gnome from other player
	 * @param player : player to take the gnome from
	 * @return false if gnome cannot be taken from player (e.g. player does not have the gnome)
	 */
	public boolean takeGnomeFrom(Player player) {
		return false;		
	}
	
	/**
	 * method to give gnome to another player
	 * @param player : player the gnome should be given to
	 * @return false if gnome cannot be given to the player (e.g. oneself does not have the gnome)
	 */
	public boolean giveGnomeTo(Player player) {
		return player.takeGnomeFrom(this);
	}
	

	public int getEndurance() {
		return endurance;
	}


	public void setEndurance(int endurance) {
		this.endurance = endurance;
	}


	public void setTeam(Team team) {
		this.team = team;
	}
	
	
	
}
