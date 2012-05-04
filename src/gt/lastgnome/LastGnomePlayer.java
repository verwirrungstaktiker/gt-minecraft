package gt.lastgnome;

import gt.general.Aura;
import gt.general.Inventory;
import gt.general.Player;

import java.awt.Point; //temporary until clear what kind of coordinates are needed
import java.util.Vector;


public class LastGnomePlayer extends Player{

	protected int maxStamina;
	protected int currentStamina;
	
	protected LastGnomeTeam team;
	protected boolean hasGnome;
	
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
		// TODO: so far there are no other items, so each player should be able to take the gnome
		return true;
	}
	
	/**
	 * method to give gnome to another player
	 * 
	 * @param player player the gnome should be given to
	 * @return false if gnome cannot be given to the player (e.g. oneself does not have the gnome)
	 */
	public boolean giveGnomeTo(LastGnomePlayer player) {
		if (this.isGnomeBearer()) {
			if (player.takeGnomeFrom(this)) {
				this.team.setGnomeBearer(player);
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * checks if a Player is the GnomeBearer
	 * 
	 * @return true if the player carries the gnome
	 */
	public boolean isGnomeBearer() {
		if (this.inventory.getActiveItem().getName() == "Gnome") {
			return true;
		}
		return false;
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