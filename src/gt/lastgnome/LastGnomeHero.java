package gt.lastgnome;

import gt.general.Inventory;
import gt.general.Hero;
import gt.general.aura.Aura;

import java.util.Vector;

import org.bukkit.Location;
import org.bukkit.entity.Player;


public class LastGnomeHero extends Hero{

	protected double maxStamina;
	protected double currentStamina;
	
	protected LastGnomeTeam team;
	protected boolean hasGnome;
	
	public Vector<Aura> auras;

	public LastGnomeHero(Player player) {
		super(player);
	}
	
	/**
	 * method to take Gnome from other Hero
	 * 
	 * @param hero Hero to take the Gnome from
	 * @return false if Gnome cannot be taken
	 */
	public boolean takeGnomeFrom(LastGnomeHero hero) {		
		//Fail-safe if hero has no Gnome
		if (!hero.isGnomeBearer()) return false;
		//Try to take Gnome
		if (!this.inventory.setActiveItem(hero.inventory.getActiveItem())) {
			//If Gnome cannot be taken, try to drop current Item
			if (this.inventory.dropActiveItem()) {
				this.inventory.setActiveItem(hero.inventory.getActiveItem());
			} else return false;
		}
		hero.inventory.setActiveItem(null);
		team.setGnomeBearer(this);
		
		return true;
	}
	
	/**
	 * method to give Gnome to another Hero
	 * 
	 * @param hero Hero the Gnome should be given to
	 * @return false if Gnome cannot be given to the Hero (e.g. oneself does not have the Gnome)
	 */
	public boolean giveGnomeTo(LastGnomeHero hero) {
		if (this.isGnomeBearer()) {
			if (hero.takeGnomeFrom(this)) {
				//this.team.setGnomeBearer(hero);
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * checks if a Hero is the GnomeBearer
	 * 
	 * @return true if the Hero carries the Gnome
	 */
	public boolean isGnomeBearer() {
		if (this.inventory.getActiveItem() == null) return false; 
		if (this.inventory.getActiveItem().getName() == "Gnome") {
			return true;
		}
		return false;
	}

	public double getMaxStamina() {
		return maxStamina;
	}

	public void setMaxStamina(double stamina) {
		this.maxStamina = stamina;
	}
	

	public double getCurrentStamina() {
		return currentStamina;
	}

	public void setCurrentStamina(double stamina) {
		if(stamina <= this.maxStamina) {
			this.currentStamina = stamina;
		}
	}


	public void setTeam(LastGnomeTeam team) {
		this.team = team;
	}
	
	
}