package gt.general.character;

import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;



public class ZombieCharacter extends Character{
	private final Zombie zombie;
	private PotionEffect  speedEffect;
	
	
	public ZombieCharacter(Zombie zombie) {
		this.zombie = zombie;
	}
	
	public Zombie getZombie() {
		return zombie;
	}
	
	private int speedToAmplifier(double speed) {
		int Result = 0;
		
		//~25% Faster
		if (speed>1.1) {
			return 1;
		}
		
		if (speed < 0.9) {
			Result = (int)Math.round(((1-speed)/0.15));
			Result = Result - 1;
			return Result;
		}

		
		return Result;
	}
	
	@Override
	public void applyAttributes() {
		Double speed = getCurrentSpeed();
		if (speedEffect == null || 
				speedEffect.getAmplifier()!= speedToAmplifier(speed)) {
			zombie.removePotionEffect(speedEffect.getType());
			if (speed < 0.9) {
				speedEffect = new PotionEffect(PotionEffectType.SLOW, 
						20*60*30, speedToAmplifier(speed));
			} else if (speed > 1.1) {
				speedEffect = new PotionEffect(PotionEffectType.SPEED, 
						20*60*30, speedToAmplifier(speed));
			}
		}
				
		
	}

}
