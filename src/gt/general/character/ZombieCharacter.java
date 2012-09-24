/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.character;

import org.bukkit.Location;
import org.bukkit.entity.PigZombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * wraps a bukkit zombie and modifies its speed
 */
public class ZombieCharacter extends Character {
	private final PigZombie zombie;
	private PotionEffect potionEffect;

	private static final int POTION_DURATION = 20 * 60 * 20;
	private static final int MAX_POTION_AMPLIFIER = 5;
	
	/**
	 * create a new zombie
	 * 
	 * @param zombie
	 *            bukkit zombie
	 */
	public ZombieCharacter(final PigZombie zombie) {
		super();
		this.zombie = zombie;
	}

	/**
	 * @return the wrapped zombie
	 */
	public PigZombie getZombie() {
		return zombie;
	}

	@Override
	public void applyAttributes() {
		if (potionEffect != null) {
			zombie.removePotionEffect(potionEffect.getType());
		}
		
		if(getCurrentSpeed() > getDefaultSpeed()) {
			potionEffect = getSpeedPotion();
			
			zombie.addPotionEffect(potionEffect);			
		} else if (getCurrentSpeed() < getDefaultSpeed()) {
			potionEffect = getSlowPotion();
			
			zombie.addPotionEffect(potionEffect);
		}
	}

	/**
	 * @return a speeding Potion
	 */
	private PotionEffect getSpeedPotion() {
		return new PotionEffect(PotionEffectType.SPEED,
								POTION_DURATION,
								speedToPotionAmplifier());
	}

	/**
	 * @return a slowing Potion
	 */
	private PotionEffect getSlowPotion() {
		
		// ensure they dont move at 0.0
		if(getCurrentSpeed() == 0.0) {
			return new PotionEffect(PotionEffectType.SLOW, POTION_DURATION, 127);
		} else {
			return new PotionEffect(PotionEffectType.SLOW,
									POTION_DURATION,
									-speedToPotionAmplifier());
		}
	}
	
	/**
	 * @return the right Potion amplifier of a specific speed
	 */
	private int speedToPotionAmplifier() {
		Double speed = getCurrentSpeed();
		Double defaultSpeed = getDefaultSpeed();
		
		return (int) ((speed - defaultSpeed) / (defaultSpeed / MAX_POTION_AMPLIFIER));
	}

	@Override
	public Location getLocation() {
		return zombie.getLocation();
	}
}
