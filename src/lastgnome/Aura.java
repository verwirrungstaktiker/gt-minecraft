package lastgnome;

import java.awt.Point; import java.util.Vector;
//temporary until clear what kind of coordinates are needed

public abstract class Aura {
	

	protected int radius;
	protected Point position;
	protected boolean permanent; //if Aura is permanent or one
	
	public Aura(Point position, boolean permanent) {
		super();
		this.position = position;
		this.permanent = permanent;
	}
	
	/**
	 * picks those agents who are in the Area of Effect
	 * @param agentList List of all agents
	 * @return agents in Area of Effect
	 */
	protected Vector<Agent> getAgentsinAoE(Vector<Agent> agentList) {
		return agentList; //dummy
	}
	
	/**
	 * Method to be called by "Aura-Manager" to let the Aura take effect
	 * @param agentList List of all agents
	 */
	abstract public void takeEffect(Vector<Agent> agentList);
		
	

}
