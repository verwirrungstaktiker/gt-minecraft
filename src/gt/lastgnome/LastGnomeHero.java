package gt.lastgnome;

import gt.general.Inventory;
import gt.general.Hero;
import gt.general.aura.Aura;

import java.util.Vector;

import org.bukkit.Location;
import org.bukkit.entity.Player;


public class LastGnomeHero extends Hero{

	protected int maxStamina;
	protected int currentStamina;
	
	protected LastGnomeTeam team;
	protected boolean hasGnome;
	
	public Vector<Aura> auras;

	public LastGnomeHero(Player player) {
		super(player);
	}
	
	/**
	 * method to take gnome from other player
	 * 
	 * @param player player to take the gnome from
	 * @return false if gnome cannot be taken
	 */
	public boolean takeGnomeFrom(LastGnomeHero player) {		
		//Fail-safe if player has no Gnome
		if (!player.isGnomeBearer()) return false;
		//Try to take Gnome
		if (!this.inventory.setActiveItem(player.inventory.getActiveItem())) {
			//If Gnome cannot be taken, try to drop current Item
			if (this.inventory.dropActiveItem()) {
				this.inventory.setActiveItem(player.inventory.getActiveItem());
			} else return false;
		}
		player.inventory.setActiveItem(null);
		team.setGnomeBearer(this);
		
		return true;
	}
	
	/**
	 * method to give gnome to another player
	 * 
	 * @param player player the gnome should be given to
	 * @return false if gnome cannot be given to the player (e.g. oneself does not have the gnome)
	 */
	public boolean giveGnomeTo(LastGnomeHero player) {
		if (this.isGnomeBearer()) {
			if (player.takeGnomeFrom(this)) {
				//this.team.setGnomeBearer(player);
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
		if (this.inventory.getActiveItem() == null) return false; 
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