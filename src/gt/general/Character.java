package gt.general;

import java.awt.Point; //temporary until clear what kind of coordinates are needed

public class Character extends Agent {
	
	protected Point orientation;	//line of sight
	protected float defaultSpeed;
	protected float currentSpeed;	//speed after (de)buff
	
	public Character(Point position, Point orientation, float defaultSpeed) {
		super();
		
		this.orientation = orientation;
		this.defaultSpeed = defaultSpeed;
		this.currentSpeed = defaultSpeed;
	}	
	
	public Point getOrientation() {
		return orientation;
	}
	
	public void setOrientation(Point orientation) {
		this.orientation = orientation;
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
}
