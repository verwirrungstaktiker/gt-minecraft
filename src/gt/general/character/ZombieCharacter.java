package gt.general.character;

import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


/**
 * wraps a bukkit zombie and modifies its speed
 */
public class ZombieCharacter extends Character{
	private final Zombie zombie;
	private PotionEffect  speedEffect;
	
	/**
	 * create a new zombie
	 * @param zombie bukkit zombie
	 */
	public ZombieCharacter(final Zombie zombie) {
		this.zombie = zombie;
	}
	
	/**
	 * @return the wrapped zombie
	 */
	public Zombie getZombie() {
		return zombie;
	}
	
	/**
	 * @param speed speed of the zombie
	 * @return the amplifier that is necessary to get a specific speed
	 */
	private int speedToAmplifier(final double speed) {
		int result = 0;
		
		
		//~25% Faster
		if (speed>1.2) {
			return (int)(speed-1)*4;
		}
		
		if (speed < 0.9) {
			result = (int)Math.round(((1-speed)/0.15));
			result = result - 1;
			return result;
		}

		
		return result;
	}
	
	@Override
	public void applyAttributes() {
		Double speed = getCurrentSpeed();
		if (speedEffect == null || 
				speedEffect.getAmplifier()!= speedToAmplifier(speed)) {
			if (speedEffect != null) {
				zombie.removePotionEffect(speedEffect.getType());
			}
			
			if (speed < 0.9) {
				speedEffect = new PotionEffect(PotionEffectType.SLOW, 
						20*60*30, speedToAmplifier(speed));
			} else if (speed > 1.1) {
				speedEffect = new PotionEffect(PotionEffectType.SPEED, 
						20*60*30, speedToAmplifier(speed));
			}
			
			if (speedEffect != null) {
				zombie.addPotionEffect(speedEffect);
			}
			
		}
				
		
	}

}
