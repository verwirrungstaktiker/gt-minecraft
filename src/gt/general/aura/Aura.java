package gt.general.aura;

import gt.general.Character;

import java.util.List;

import org.bukkit.entity.Entity;

public class Aura {

	private Character owner;
	private final EffectFactory effectFactory;

	private int distance;
	private int duration;
	
	public static int INFINITE_DURATION = Integer.MIN_VALUE;

	/**
	 * generates a new Aura with infinite duration
	 * 
	 * @see Aura#Aura(EffectFactory, int, int)
	 */
	public Aura(final EffectFactory effect, int distance) {
		this(effect, distance, INFINITE_DURATION);
	}

	/**
	 * generates a new Aura
	 * 
	 * @param effectFactory
	 *            a factory for the Effect to be spread
	 * @param distance
	 *            the distance in which the Effect is applied to Entities
	 * @param duration
	 *            after duration Ticks the Aura is discarded
	 */
	public Aura(final EffectFactory effectFactory, int distance, int duration) {
		this.owner = null;
		this.effectFactory = effectFactory;
		this.distance = distance;
		this.duration = duration;
	}

	/**
	 * @param owner
	 *            the owner of this Aura
	 */
	public void setOwner(Character owner) {
		if (this.owner != null) {
			throw new RuntimeException("Cannot reassign owner");
		}
		this.owner = owner;
	}

	/**
	 *  performs one tick - duration management and Effect spreading
	 */
	public void performTick() {		
		if (duration == INFINITE_DURATION || duration-- > 0) {
			spreadEffect();
		}
		else {
			owner.getAuras().remove(this);
		}
	}
	
	/**
	 * spreads the effect of this aura to all Characters in distance
	 */
	private void spreadEffect() {
		List<Entity> nearbyEntities = owner.getNearbyEntities(distance,
				distance, distance);
		
		for(Entity entity : nearbyEntities) {
			if (entity instanceof Character) {
				((Character)entity).addEffect(effectFactory.getEffect());
			}
		}
	}
}
