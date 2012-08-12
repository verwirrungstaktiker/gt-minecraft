package gt.general.character;

import static com.google.common.collect.Maps.*;
import gt.general.aura.Aura;

import java.util.Map;
import java.util.Vector;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * a Manager for Zombies of an instance 
 * @author philipp
 *
 */
public class ZombieManager implements Listener, Runnable{
	
	private LivingEntity target;
	private Vector<ZombieCharacter> zombies;
	private final World world;
	//private int taskID;
	
	//TODO this really needs to be done better
	private boolean allowDamage;
	
	private boolean frozen = false;
	private final Map<ZombieCharacter, Location> frozenPositions = newHashMap();
	private Vector<ZombieCharacter> mob;
	
	/**
	 * Creates a new ZombieManager
	 * @param world the world, where the Zombies will be spawned
	 */
	public ZombieManager(final World world) {
		zombies = new Vector<ZombieCharacter>();
		this.world = world;
		allowDamage = false;
		mob = new Vector<ZombieCharacter>();
	}
	
	/**
	 * set the id of this task
	 * @param id task id
	 */
	/*public void setTaskID(final int id) {
		taskID = id;
	}*/

	
	/**
	 *  Handles events, when Zombie hits or is hit
	 * @param event an EntityDamageByEntity Event
	 */
	@EventHandler
	public void damageEntityEvent(final EntityDamageByEntityEvent event) {
		//On hit, Zombie drains 8 half hearts
		if (event.getDamager() instanceof Zombie) {
			event.setDamage(8);
		}
		
		//Zombies cannot be harmed by Players

		if (event.getDamager() instanceof Player) {
			if (event.getEntity() instanceof Zombie) {
				event.setDamage(0);
				event.setCancelled(!allowDamage);
			} else {
				event.setCancelled(true);
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
	 * @param hero the new target
	 */
	public void setTarget(final Hero hero) {
		
		if(hero != null) {
			target = hero.getPlayer();
		}		
	}

	/**
	 * Spawns a Zombie
	 * @param spawnpoint location, where zombie should be spawned
	 */
	public void spawnZombie(final Location spawnpoint) {
		spawnZombie(spawnpoint,1.0);
	}

	/**
	 * Spawns a Zombie
	 * @param spawnpoint location, where zombie should be spawned
	 */
	public void spawnZombieMob(final Location spawnpoint, int number) {
		for (int i=0;i<number;++i) {
			spawnZombie(spawnpoint,1.0);			
		}
	}	
	
	/**
	 * Spawns a Zombie with different speed
	 * @param spawnpoint location, where zombie should be spawned
	 * @param speed the Zombies basic speed-multiplicator
	 */
	public void spawnZombie(final Location spawnpoint, final double speed) {
		spawnZombie(spawnpoint, null, speed);
	}
	
	/**
	 * Spawns a Zombie with different speed and an aura
	 * @param spawnpoint location, where zombie should be spawned
	 * @param aura an Aura which originates from the zombie 
	 * @param speed the Zombies basic speed-multiplicator
	 * 
	 */
	public void spawnZombie(final Location spawnpoint, final Aura aura, final double speed) {
		ZombieCharacter zombie = new ZombieCharacter(world.spawn(spawnpoint, PigZombie.class));
		zombie.setAttribute(CharacterAttributes.SPEED, speed);
		//make sure not to add null-auras
		if (aura != null) {
			zombie.addAura(aura);
		}
		zombies.add(zombie);
	}
	
	/**
	 * remove all zombies & cancel the schedule
	 */
	public void cleanup() {
		//Hello.cancelScheduledTask(taskID);
		clearZombies();
	}

	/**
	 * Kills and removes all Zombies
	 */
	public void clearZombies() {
		for (ZombieCharacter zombie : zombies) {
			zombie.getZombie().getWorld().playEffect(
					zombie.getZombie().getLocation(), Effect.POTION_BREAK, 10);
			//zombie.getZombie().damage(20);
			zombie.getZombie().remove();
		}
		zombies.clear();
	}
	
	public void clearMob() {
		for (ZombieCharacter zombie : mob) {
			zombie.getZombie().getWorld().playEffect(
					zombie.getZombie().getLocation(), Effect.POTION_BREAK, 10);
			//zombie.getZombie().damage(20);
			zombie.getZombie().remove();
		}
		mob.clear();
	}
	
	/**
	 * Modifies Speed of all Zombies
	 * @param value Value to be added to Zombie speed
	 */
	public void addSpeedAll(final Double value) {
		for (ZombieCharacter zombie : zombies) {
			zombie.addToAttribute(CharacterAttributes.SPEED, value);
			zombie.applyAttributes();
		}
	}
	

	
	
	/**
	 * Scheduled Method to make sure zombies attack right target
	 */
	@Override
	public void run() {
		
		if(frozen) {
			relocateZombies();
		}
		
		if (target == null) {
			return;
		}
		
		for (ZombieCharacter zombieChar : zombies) {
			Zombie zombie = zombieChar.getZombie();
			//See if any Players are nearby
			for (Entity entity : zombie.getNearbyEntities(1.5, 1.5, 1.5)) {
				if (entity.getType() == EntityType.PLAYER) {
					//target players who are to close
					if (zombie.getTarget() == null ||!zombie.getTarget().equals(entity)) {
						//allowDamage = true;
						zombie.setTarget((LivingEntity) entity);
						//allowDamage = false;
					}
					return;
				}
			}
			
			//If no player is to close, target the target
			if (zombie.getTarget() == null || !zombie.getTarget().equals(target)) {
				zombie.setTarget(target);
			}
			
		}
	}

	
	private void relocateZombies() {
		for(ZombieCharacter zombie : frozenPositions.keySet()) {
			
			zombie.getZombie().teleport(frozenPositions.get(zombie));
		}
	}
	
	public void freezeAllZombies() {
		frozen = true;
		
		for(ZombieCharacter zombie : zombies) {
			frozenPositions.put(zombie, zombie.getLocation());
		}
	}

	public void unFreezeAllZombies() {
		frozen = false;
		frozenPositions.clear();
	}

}
