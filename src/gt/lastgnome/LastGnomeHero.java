package gt.lastgnome;

import gt.general.Hero;
import gt.general.aura.Aura;
import gt.general.aura.Effect;
import gt.general.aura.GnomeCarrierEffect;

import java.util.Vector;

import org.bukkit.entity.Player;

public class LastGnomeHero extends Hero {

	protected double maxStamina;
	protected double currentStamina;

	protected LastGnomeTeam team;
	protected boolean hasGnome;

	public LastGnomeHero(Player player) {
		super(player);
	}

	/**
	 * method to take Gnome from other Hero
	 * 
	 * @param hero
	 *            Hero to take the Gnome from
	 * @return false if Gnome cannot be taken
	 */
	public boolean takeGnomeFrom(final LastGnomeHero hero) {
		// Fail-safe if hero has no Gnome
		if (!hero.isGnomeBearer()) {
			return false;
		}
		// Try to take Gnome
		if (!getInventory().setActiveItem(
				hero.getInventory().getActiveItem())) {
			// If Gnome cannot be taken, try to drop current Item
			if (getInventory().dropActiveItem()) {
				getInventory().setActiveItem(
						hero.getInventory().getActiveItem());
			} else {
				return false;
			}
		}
		hero.getInventory().setActiveItem(null);
		team.setGnomeBearer(this);

		team.getGnome().getGnomeAura().setOwner(this);	
		
		return true;
	}

	/**
	 * method to give Gnome to another Hero
	 * 
	 * @param target
	 *            Hero the Gnome should be given to
	 * @return false if Gnome cannot be given to the Hero (e.g. oneself does not
	 *         have the Gnome)
	 */
	public boolean giveGnomeTo(final LastGnomeHero target) {
		if (isGnomeBearer() && target.takeGnomeFrom(this)) {
			
			team.getGnome().getGnomeAura().setOwner(null);

			// remove all GnomeCarrierEffects, if the gnome is lost
			Vector<Effect> effects = getEffects();
			for (Effect effect : effects) {
				if (effect instanceof GnomeCarrierEffect) {
					effects.remove(effect);
				}
			}

			// TODO add post gnome effect
			
			return true;
		}
		return false;
	}

	/**
	 * checks if a Hero is the GnomeBearer
	 * 
	 * @return true if the Hero carries the Gnome
	 */
	public boolean isGnomeBearer() {
		return getInventory().getActiveItem() instanceof Gnome;
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
		if (stamina <= this.maxStamina) {
			this.currentStamina = stamina;
		}
	}

	public void setTeam(LastGnomeTeam team) {
		this.team = team;
	}

}