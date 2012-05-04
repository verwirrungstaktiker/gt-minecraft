package gt.general;

import java.awt.Point; //temporary until clear what kind of coordinates are needed


public class Agent {

	protected Point position;
	protected Point orientation;	//line of sight
	protected float defaultSpeed;
	protected float currentSpeed;	//speed after (de)buff
	
	public Agent(Point position, Point orientation, float defaultSpeed) {
		super();
		
		this.position = position;
		this.orientation = orientation;
		this.defaultSpeed = defaultSpeed;
		this.currentSpeed = defaultSpeed;
	}


	public Point getPosition() {
		return position;
	}
	
	//necessary for tests and later for teleporting
	public void setPosition(Point position) {
		this.position = position;
	}
	
	
	public Point getOrientation() {
		return position;
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
