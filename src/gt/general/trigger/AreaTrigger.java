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
	
	public void setLocation(Location loc) {
		this.loc = loc;
	}
	
	/**
	 * Checks if a number is between two other numbers.
	 * 
	 * @param border1 threshold number 1
	 * @param val number that is checked
	 * @param border2 threshold number 2
	 * @return true if val is between the other to numbers
	 */
	private boolean between (int border1, int val, int border2) {
		if ( (val >= border1 && val <= border2) || (val <= border1 && val >= border2) ) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	/**
	 * Checks if Location is in Cube
	 */
	public void checkTrigger() {
		if (between(cube[0].getBlockX(), loc.getBlockX(), cube[1].getBlockX())) {
			if (between(cube[0].getBlockY(), loc.getBlockY(), cube[1].getBlockY())) {
				if (between(cube[0].getBlockZ(), loc.getBlockZ(), cube[1].getBlockZ())) {
					if (!repeat) {
						tm.deregisterTrigger(this);
					}
					callback.run();
				}
			}
					
		}
			
		
		
	}

	

}
