/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.character;

import gt.general.aura.Aura;
import gt.general.aura.Effect;
import gt.general.aura.FreezeEffect;
import gt.general.aura.FreezeEffect.FreezeCause;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.bukkit.Location;

/**
 * our representation of Minecraft's LivingEntity
 */
public abstract class Character {

	/**Auras originating from the player*/
	private final Vector<Aura> auras = new Vector<Aura>();
	/**Effects affecting the player*/
	private final Vector<Effect> effects = new Vector<Effect>();

	/**unmodified Attributes*/
	private final Map<CharacterAttributes, Double> baseAttributes = new HashMap<CharacterAttributes, Double>();
	/**modified Attributes*/
	private final Map<CharacterAttributes, Double> computedAttributes = new HashMap<CharacterAttributes, Double>();

	private boolean computedAttributesTainted = true;

	/**
	 * creates a new Character
	 *
	 * @param defaultHeroSpeed
	 */
	public Character() {

		setAttribute(CharacterAttributes.HEALTH, 1.0);
		setAttribute(CharacterAttributes.SPEED, 100.0);
		setAttribute(CharacterAttributes.JUMPMULTIPLIER, 1.0);
	}

	/**
	 * @return character's current speed
	 */
	public double getCurrentSpeed() {
		return Math.max(computedAttributes.get(CharacterAttributes.SPEED), 0);
	}

	/**
	 * @return character's default speed
	 */
	public double getDefaultSpeed() {
		return baseAttributes.get(CharacterAttributes.SPEED);
	}

	/**
	 * @return the current Location of this Character
	 */
	public abstract Location getLocation();

	/**
	 * @return the auras
	 */
	public Vector<Aura> getAuras() {
		return auras;
	}

	/**
	 * @return the effects
	 */
	public Vector<Effect> getEffects() {
		return effects;
	}

	/**
	 * @param aura
	 *            the aura to be added to this character
	 */
	public void addAura(final Aura aura) {
		aura.setOwner(this);
		auras.add(aura);
	}

	/**
	 * @param effect
	 *            The effect to be added to this character
	 */
	public void addEffect(final Effect effect) {
		computedAttributesTainted = true;
		effects.add(effect);
	}


	/**
	 * @param effect The effect to be removed from this character
	 */
	public void removeEffect(final Effect effect) {
		computedAttributesTainted = true;
		effects.remove(effect);
	}

	/**
	 * adds an attribute to the collection of attributes of this character
	 *
	 * @param attribute
	 *            Type of the Attribute
	 * @param base
	 *            Immutable base value of the attribute
	 */
	protected void setAttribute(final CharacterAttributes attribute,
			final double base) {
		baseAttributes.put(attribute, base);
		computedAttributes.put(attribute, base);
	}

	/**
	 * @param attribute
	 *            Type of the attribute
	 * @return Immutable base value
	 */
	public double getBaseAttribute(final CharacterAttributes attribute) {
		return baseAttributes.get(attribute);
	}

	/**
	 * the value must be calculated on base of the baseAttribute - not on the
	 * current computed value
	 *
	 * @param attribute
	 *            the attribute to be modified
	 * @param value
	 *            the value to be added to the attribute
	 */
	public void addToAttribute(final CharacterAttributes attribute,
			final double value) {
		computedAttributes.put(attribute,
				computedAttributes.get(attribute) + value);
	}

	/**
	 * @param attribute
	 *            the attribute to be modified
	 * @param value
	 *            the value which is multiplied to the current value
	 */
	public void scaleAttribute(final CharacterAttributes attribute,
			final double value) {
		computedAttributes.put(attribute,
				computedAttributes.get(attribute) * value);
	}

	/**
	 * performs the attribute updates on a tick
	 */
	public void simulateEffects() {

		Iterator<Effect> it = effects.iterator();
		while(it.hasNext()) {
			Effect e = it.next();
			
			if(e.remainingTicks() == 0) {
				it.remove();
				computedAttributesTainted = true;
			} else {
				e.performTick();
			}
		}

		if (computedAttributesTainted) {
			calculateAttributes();
			applyAttributes();
			computedAttributesTainted = false;
		}
	}

	/**
	 * calculates the attribute values on base of the current effects
	 */
	protected void calculateAttributes() {
		for (CharacterAttributes attribute : baseAttributes.keySet()) {
			computedAttributes.put(attribute,
					baseAttributes.get(attribute));
		}

		Collections.sort(effects);
		
		for (Effect effect : effects) {
			effect.takeEffect(this);
		}

	}

	/**
	 * Applys the calculated Attributes to the ingame model
	 */
	public abstract void applyAttributes();

	/**
	 * returns the computed value of the attribute
	 *
	 * @param attr the attribute to be retrieved
	 * @return the value of the attribute
	 */
	public double getAttribute(final CharacterAttributes attr) {
		return computedAttributes.get(attr);
	}

	/**
	 * @param computedAttributesTainted the computedAttributesTainted to set
	 */
	public void setComputedAttributesTainted(final boolean computedAttributesTainted) {
		this.computedAttributesTainted = computedAttributesTainted;
	}
	
	public void pause() {
		addEffect(new FreezeEffect(FreezeCause.PAUSE));
	}

	public void freeze() {
		addEffect(new FreezeEffect(FreezeCause.FREEZE));
	}
	
	public void resume(FreezeCause cause) {
		
		// search all freeze effects and remove them
		Iterator<Effect> it = getEffects().iterator();
		while(it.hasNext()) {
			Effect effect = it.next();
		    if (effect instanceof FreezeEffect) {
		    	FreezeEffect fEffect = (FreezeEffect) effect;
		    	if(fEffect.getCause()==cause) {
			    	System.out.println("found freeze caused by " + cause);
			        it.remove();
		    	}
		    }
		}
		
		setComputedAttributesTainted(true);
	}
	

}
