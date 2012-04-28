package general;

import java.awt.Point;	//temporary until clear what kind of coordinates are needed
import java.util.Vector;

public abstract class Aura {
	
	protected Point position;
	protected int radius;
	protected boolean permanent;	//permanent (de)buff if true
	
	public Aura(int radius, Point position, boolean permanent) {
		super();
		
		this.radius = radius;
		this.position = position;
		this.permanent = permanent;
	}
	
	/**
	 * picks those agents who are in the Area of Effect
	 * 
	 * @param agentList List of all agents
	 * @return agents in Area of Effect
	 */
	protected Vector<Agent> getAgentsInAOE(Vector<Agent> agentList) {
		return agentList; //TODO: dummy
	}
	
	/**
	 * Method to be called by "Aura-Manager" to let the Aura take effect
	 * 
	 * @param agentList List of all agents
	 */
	abstract public void takeEffect(Vector<Agent> agentList);
		
	

}
