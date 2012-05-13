package gt.general.trigger;

import org.bukkit.Location;

public class AreaTrigger extends Trigger{

	//Areas which describe the 8 Points of the Trigger-Cube
	protected Location[] cube;
	//Location to be monitored
	protected Location loc;
	
	/**
	 * 
	 * @param cube Array with 2 Points defining the Cube, first being the "lower" point
	 * @param loc Location to be Monitored
	 * @param repeat true if the trigger should be repeatable
	 * @param tm the TriggerManager
	 */
	public AreaTrigger (Location[] cube, Location loc, boolean repeat, Runnable callback, TriggerManager tm) {
		super(repeat,callback,tm);
		this.cube = cube;
		this.loc = loc;
		
	}
	
	public void changeLocation(Location loc) {
		this.loc = loc;
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
	public void checkTrigger() {
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
