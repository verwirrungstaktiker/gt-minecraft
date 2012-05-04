package gt.general;

import gt.general.aura.Aura;
import gt.general.aura.Effect;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import org.bukkit.Location;

public class Character {
	
	protected float defaultSpeed;
	protected float currentSpeed;	//speed after (de)buff
	
	private final Vector<Aura> auras = new Vector<Aura>();
	private final Vector<Effect> effects = new Vector<Effect>();
	
	/**
	 * creates a new Character
	 * 
	 * @param defaultSpeed
	 */
	public Character(float defaultSpeed) {
		super();
		
		this.defaultSpeed = defaultSpeed;
		this.currentSpeed = defaultSpeed;
	}	

	public float getCurrentSpeed() {
		return currentSpeed;
	}

	public void setCurrentSpeed(float speed) {
		this.currentSpeed = speed;
	}
	
	
	public float getDefaultSpeed() {
		return defaultSpeed;
	}
	
	public void setDefaultSpeed(float speed) {
		this.defaultSpeed = speed;
	}

	/**
	 * @return the current Location of this Character
	 */
	public Location getLocation() {
		// TODO this should be read directly from bukket
		return null;
	}
}
