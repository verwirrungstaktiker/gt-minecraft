package gt.general.character;

import gt.general.aura.Aura;

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
	
	private boolean frozen = false;
	
	/**
	 * Creates a new ZombieManager
	 * @param world the world, where the Zombies will be spawned
	 */
	public ZombieManager(final World world) {
		zombies = new Vector<ZombieCharacter>();
		this.world = world;
	}

	
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
		clearZombies();
	}

	/**
	 * Kills and removes all Zombies
	 */
	public void clearZombies() {
		for (ZombieCharacter zombie : zombies) {
			zombie.getZombie().getWorld().playEffect(
					zombie.getZombie().getLocation(), Effect.POTION_BREAK, 10);
			zombie.getZombie().remove();
		}
		zombies.clear();
	}
	
	/**
	 * Modifies Speed of all Zombies
	 * @param value Value to be added to Zombie speed
	 */
	public void addSpeedAll(final Double value) {
		for (ZombieCharacter zombie : zombies) {
			zombie.addToAttribute(CharacterAttributes.SPEED, value);
		}
	}
	

	
	
	/**
	 * Scheduled Method to make sure zombies attack right target
	 */
	@Override
	public void run() {
		
		for (ZombieCharacter zombie : zombies) {
			zombie.applyAttributes();
		}
		
		if(frozen) {
			return;
		}
		
		if (target == null) {
			return;
		}
		
		for (ZombieCharacter zombieChar : zombies) {
			PigZombie zombie = zombieChar.getZombie();
			//See if any Players are nearby
			for (Entity entity : zombie.getNearbyEntities(1.5, 1.5, 1.5)) {
				if (entity.getType() == EntityType.PLAYER) {
					//target players who are to close
					if (zombie.getTarget() == null ||!zombie.getTarget().equals(entity)) {
						zombie.setTarget((LivingEntity) entity);
					}
					break;
				}
			}
			
			//If no player is to close, target the target
			if (zombie.getTarget() == null || !zombie.getTarget().equals(target)) {
				zombie.setTarget(target);
			}
			
			if (distanceToTarget(zombie)>10) {
				double value = 1.5-zombieChar.getCurrentSpeed();
				zombieChar.addToAttribute(CharacterAttributes.SPEED, value);
				break;
			}
			
			if (zombieChar.getCurrentSpeed()>1.4 
					&& distanceToTarget(zombie)<6.2) {
				double value = 1-zombieChar.getCurrentSpeed();
				zombieChar.addToAttribute(CharacterAttributes.SPEED, value);
			}
			
		}
	}
	
	/**
	 * Teleports all zombies to a location
	 * @param teleport location where zombies shpuld be teleported to
	 */
	public void teleportZombies(Location teleport) {
		for (ZombieCharacter zombie : zombies) {
			zombie.getZombie().teleport(teleport);
			zombie.getZombie().getWorld().playEffect(
					zombie.getZombie().getLocation(), Effect.POTION_BREAK, 10);
		}
	}

	/**
	 * calculates the distance between zombie and its target
	 * @param zombie the zombie
	 * @return the distance
	 */
	private double distanceToTarget(PigZombie zombie) {
		Location z_loc = zombie.getLocation();
		Location t_loc = zombie.getTarget().getLocation();
		return z_loc.distance(t_loc);
	}
	
	/**
	 * toggles if zombies should be frozen
	 */
	public void toggleFreeze() {
		frozen = !frozen;
		if (frozen) {
			addSpeedAll(-3.0);
		} else {
			addSpeedAll(3.0);
		}
	}
	
	public void freezeAllZombies() {
		if (!frozen) {
			toggleFreeze();
		}
	}

	public void unFreezeAllZombies() {
		if (frozen) {
			toggleFreeze();
		}
	}

}
