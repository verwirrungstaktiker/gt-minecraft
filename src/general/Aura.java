package general;

import java.awt.Point;	//temporary until clear what kind of coordinates are needed
import java.util.Iterator;
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
	 * filters those agents who are in the Area of Effect
	 * 
	 * @param agentList Vector of all agents
	 * @return agents in Area of Effect
	 */
	protected Vector<Agent> getAgentsInAOE(Vector<Agent> agentList) {
		Vector<Agent> affectedAgents = new Vector<Agent>();
		
		//filter the agents that are in range of the aura
		Iterator<Agent> it = agentList.iterator();
		while (it.hasNext()) {
			if(agentInAOE(it.next())) {
				affectedAgents.addElement(it.next());
			}
		}
		
		return affectedAgents;
	}
	
	
	/**
	 * Checks if an agent is in Area of Effect
	 * 
	 * @param agent Player or Mob
	 * @return true if Agent is in Area of Effect
	 */
	private boolean agentInAOE(Agent agent) {
		if (radius >= position.distance(agent.getPosition())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method to be called by "Aura-Manager" to let the Aura take effect
	 * 
	 * @param agentList List of all agents
	 */
	abstract public void takeEffect(Vector<Agent> agentList);
		
	

}
