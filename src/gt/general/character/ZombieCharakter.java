package gt.general.character;

import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;



public class ZombieCharakter extends Character{
	private final Zombie zombie;
	private PotionEffect  speedEffect;
	
	
	public ZombieCharakter(Zombie zombie) {
		this.zombie = zombie;
	}
	
	public Zombie getZombie() {
		return zombie;
	}
	
	private int speedToAmplifier(double speed) {
		int Result = 0;
		
		if (speed > 1) {
			Result = (int) ((speed-1) * 100);
		}
		
		if (speed < 1) {
			Result = (int) ((1-speed) * 100);
		}
		return Result;
	}
	
	@Override
	public void applyAttributes() {
		Double speed = getCurrentSpeed();
		if (speedEffect == null || 
				speedEffect.getAmplifier()!= speedToAmplifier(speed)) {
			zombie.removePotionEffect(speedEffect.getType());
			if (speed < 1) {
				speedEffect = new PotionEffect(PotionEffectType.SLOW, 
						20*60*30, speedToAmplifier(speed));
			} else if (speed > 1) {
				speedEffect = new PotionEffect(PotionEffectType.SPEED, 
						20*60*30, speedToAmplifier(speed));
			}
		}
				
		
	}

}
