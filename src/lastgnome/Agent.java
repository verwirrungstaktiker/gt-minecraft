package lastgnome;

import java.awt.Point; //temporary until clear what kind of coordinates are needed


public class Agent {

	protected Point position;
	protected float speed; //factor for speed
	
	public Agent(Point position) {
		super();	 
	}


	public Point getPosition() {
		return position;
	}
	
	//necessary for tests and later for teleporting
	public void setPosition(Point position) {
		this.position = position;
	}


	public float getSpeed() {
		return speed;
	}


	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
}
