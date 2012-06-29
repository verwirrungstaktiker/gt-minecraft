package gt.general.character;

import gt.general.aura.Aura;
import gt.plugin.helloworld.HelloWorld;

import java.util.Vector;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Listener;

/**
 * a Manager for Zombies of an instance 
 * @author philipp
 *
 */
public class ZombieManager implements Listener, Runnable{
	
	private LivingEntity target;
	private Vector<ZombieCharacter> zombies;
	private final World world;
	private int taskID;
	
	/**
	 * Creates a new ZombieManager
	 * @param world the world, where the Zombies will be spawned
	 */
	public ZombieManager(World world) {
		zombies = new Vector<ZombieCharacter>();
		this.world = world;
	}
	
	public void setTaskID(int id) {
		taskID = id;
	}

	
	/**
	 *  Handles events, when Zombie hits or is hit
	 * @param event an EntityDamageByEntity Event
	 */
	@EventHandler
	public void damageEntityEvent(EntityDamageByEntityEvent event) {
		//On hit, Zombie drains 1/3 MaxHealth
		if (event.getDamager() instanceof Zombie) {
			LivingEntity p = (LivingEntity) event.getEntity();
			event.setDamage(p.getMaxHealth()/3);
		}
		
		//Zombies cannot be harmed by Players
		if (event.getEntity() instanceof Zombie) {
			if (event.getDamager() instanceof Player) {
			event.setDamage(0);
			}
		}
		
	}
	
	/**
	 * 
	 * @return current Target of Zombies
	 */
	public LivingEntity getTarget() {
		return target;
	}

	/**
	 * Set a new target for Zombies
	 * @param target the new target
	 */
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
		spawnZombie(spawnpoint, null, speed);
	}
	
	/**
	 * Spawns a Zombie with different speed and an aura
	 * @param spawnpoint location, where zombie should be spawned
	 * @param aura an Aura which originates from the zombie 
	 * @param speed the Zombies basic speed-multiplicator
	 * 
	 */
	public void spawnZombie(Location spawnpoint, Aura aura, double speed) {
		ZombieCharacter zombie = new ZombieCharacter(world.spawn(spawnpoint, Zombie.class));
		zombie.setAttribute(CharacterAttributes.SPEED, speed);
		//make sure not to add null-auras
		if (aura != null) {
			zombie.addAura(aura);
		}
		zombies.add(zombie);
	}
	
	public void cleanup() {
		clearZombies();
		HelloWorld.getPlugin().getServer().getScheduler()
		.cancelTask(taskID);
		
	}

	/**
	 * Removes all Zombies
	 */
	public void clearZombies() {
		for (ZombieCharacter zombie : zombies) {
			zombie.getZombie().remove();
			zombies.remove(zombie);
		}
	}
	
	/**
	 * Scheduled Method to make sure zombies attack right target
	 */
	@Override
	public void run() {
		if (target == null) return;
		for (ZombieCharacter zombieChar : zombies) {
			Zombie zombie = zombieChar.getZombie();
			//I hope at least getTarget works
			if (!zombie.getTarget().equals(target)) {
				zombie.damage(0, target);
			}
		}
	}

}
