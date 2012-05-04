package gt.general.aura;

import gt.general.Character;

import java.util.Iterator;
import java.util.Vector;

import org.bukkit.Location;

public abstract class Aura {
	
	protected Location location;
	protected int radius;
	protected boolean permanent;	//permanent (de)buff if true
	
	public Aura(int radius, Location location, boolean permanent) {
		super();
		
		this.radius = radius;
		this.location = location;
		this.permanent = permanent;
	}
	
	/**
	 * filters those agents who are in the Area of Effect
	 * 
	 * @param agentList Vector of all agents
	 * @return agents in Area of Effect
	 */
	protected Vector<Character> getAgentsInAOE(Vector<Character> agentList) {
		Vector<Character> affectedAgents = new Vector<Character>();
		
		//filter the agents that are in range of the aura
		Iterator<Character> it = agentList.iterator();
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
	private boolean agentInAOE(Character agent) {
		if (radius >= location.distance(agent.getLocation())) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method to be called by "Aura-Manager" to let the Aura take effect
	 * 
	 * @param agentList List of all agents
	 */
	abstract public void takeEffect(Vector<Character> agentList);
		
	

}
