package gt.general.trigger;

import org.bukkit.Location;

public class AreaTrigger implements Runnable{

	//Areas which describe the 8 Points of the Trigger-Cube
	protected Location[] cube;
	protected Runnable callback;
	protected TriggerManager tm;
	protected boolean repeat;
	//Location to be monitored
	protected Location loc;
	
	/**
	 * 
	 * @param cube Array with 2 Points defining the Cube, 0 it "lower" point
	 * @param loc Location to be Monitored
	 * @param repeat true if the trigger should be repeatable
	 * @param tm the TriggerManager
	 */
	public AreaTrigger (Location[] cube, Location loc, boolean repeat, TriggerManager tm) {
		this.cube = cube;
		this.repeat = repeat;
		this.loc = loc;
		this.tm = tm;
		tm.registerTrigger(this);
	}
	
	protected boolean between (int min,int val,int max) {
		if (val>=min && val <= max)
			return true;
		else 
			return false;
	}
	
	@Override
	/**
	 * Checks if Location is in Cube
	 */
	public void run() {
		if (between(cube[0].getBlockX(),loc.getBlockX(),cube[1].getBlockX())) {
			if (between(cube[0].getBlockY(),loc.getBlockY(),cube[1].getBlockY())) {
				if (between(cube[0].getBlockZ(),loc.getBlockZ(),cube[1].getBlockZ())) {
					if (!repeat) tm.deregisterTrigger(this);
					callback.run();
				}
			}
					
		}
			
		
		
	}

	

}
