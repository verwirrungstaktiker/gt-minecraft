package gt.general.character;

import java.util.Vector;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ZombieManager implements Listener, Runnable{
	
	private LivingEntity target;
	private Vector<ZombieCharakter> zombies;
	private final World world;
	
	public ZombieManager(World world) {
		zombies = new Vector<ZombieCharakter>();
		this.world = world;
	}
	
	
	//Zombies are invincible
	@EventHandler
	public void damageEvent(EntityDamageEvent event) {
		if (event.getEntity() instanceof Zombie) {
			event.setDamage(0);
		}
		
	}
	
	//On hit, Zombie drains 1/3 MaxHealth
	@EventHandler
	public void damageEntityEvent(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Zombie) {
			LivingEntity p = (LivingEntity) event.getEntity();
			event.setDamage(p.getMaxHealth()/3);
		}
		
	}
	
	public LivingEntity getTarget() {
		return target;
	}

	public void setTarget(LivingEntity target) {
		this.target = target;
	}

	/**
	 * Spawns a Zombie
	 * @param spawnpoint location, where zombie should be spawned
	 */
	public void spawnZombie(Location spawnpoint) {
		spawnZombie(spawnpoint,1.0);
	}
	
	/**
	 * Spawns a Zombie with different speed
	 * @param spawnpoint location, where zombie should be spawned
	 * @param speed the Zombies basic speed-multiplicator
	 */
	public void spawnZombie(Location spawnpoint,double speed) {
		ZombieCharakter zombie = new ZombieCharakter(world.spawn(spawnpoint, Zombie.class));
		zombie.setAttribute(CharacterAttributes.SPEED, speed);
		zombies.add(zombie);
	}
	

	/**
	 * Removes all Zombies
	 */
	public void clearZombies() {
		for (ZombieCharakter zombie : zombies) {
			zombie.getZombie().remove();
			zombies.remove(zombie);
		}
	}
	
	@Override
	public void run() {
		for (ZombieCharakter zombieChar : zombies) {
			Zombie zombie = zombieChar.getZombie();
			//I hope at least getTarget works
			if (!zombie.getTarget().equals(target)) {
				zombie.damage(0, target);
			}
		}
	}

}
