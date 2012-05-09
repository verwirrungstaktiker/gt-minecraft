package gt.general;

import gt.general.aura.Aura;
import gt.general.aura.Effect;
import gt.general.aura.EffectFactory;

import java.util.List;
import java.util.Vector;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class Character {
	
	protected double defaultSpeed;
	protected double currentSpeed;	//speed after (de)buff
	
	private final Vector<Aura> auras = new Vector<Aura>();
	private final Vector<EffectFactory> effects = new Vector<EffectFactory>();
	
	/**
	 * creates a new Character
	 * 
	 * @param defaultHeroSpeed
	 */
	public Character(double defaultHeroSpeed) {
		super();
		
		this.defaultSpeed = defaultHeroSpeed;
		this.currentSpeed = defaultHeroSpeed;
	}	

	public double getCurrentSpeed() {
		return currentSpeed;
	}

	public void setCurrentSpeed(double speed) {
		this.currentSpeed = speed;
	}
	
	
	public double getDefaultSpeed() {
		return defaultSpeed;
	}
	
	public void setDefaultSpeed(double speed) {
		this.defaultSpeed = speed;
	}

	/**
	 * @return the current Location of this Character
	 */
	public Location getLocation() {
		return null;
	}

	/**
	 * @return the auras
	 */
	public Vector<Aura> getAuras() {
		return auras;
	}
	
	/**
	 * @param aura the aura to be added to this character
	 */
	public void addAura(Aura aura) {
		aura.setOwner(this);
		auras.add(aura);
	}

	/**
	 * @return the effects
	 */
	public Vector<EffectFactory> getEffects() {
		return effects;
	}
	
	public void addEffect(Effect effect) {
		// TODO Auto-generated method stub
		
	}

	public List<Entity> getNearbyEntities(int distance, int distance2,
			int distance3) {
		// TODO Auto-generated method stub
		return null;
	}
}
