package lastgnome;

import general.Aura;
import general.Inventory;
import general.Player;

import java.awt.Point; //temporary until clear what kind of coordinates are needed
import java.util.Vector;


public class LastGnomePlayer extends Player{

	protected int maxStamina;
	protected int currentStamina;
	protected LastGnomeTeam team;
	public Inventory inventory;	
	public Vector<Aura> auras;
	
	public LastGnomePlayer(Point position, Point orientation, float defaultSpeed) {
		super(position, orientation, defaultSpeed);
	}	

	/**
	 * method to take gnome from other player
	 * 
	 * @param player player to take the gnome from
	 * @return false if gnome cannot be taken
	 */
	public boolean takeGnomeFrom(LastGnomePlayer player) {
		return false;
	}
	
	/**
	 * method to give gnome to another player
	 * 
	 * @param player player the gnome should be given to
	 * @return false if gnome cannot be given to the player (e.g. oneself does not have the gnome)
	 */
	public boolean giveGnomeTo(LastGnomePlayer player) {
		return player.takeGnomeFrom(this);
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


	public void setTeam(LastGnomeTeam team) {
		this.team = team;
	}
	
	
}