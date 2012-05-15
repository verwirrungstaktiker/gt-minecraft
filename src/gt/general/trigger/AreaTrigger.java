package gt.general.trigger;

import org.bukkit.Location;
/**
 * Trigger which checks if a location is in a cube
 */
public class AreaTrigger extends Trigger{

	/**The two locations describing the cube*/
	protected Location[] cube;
	/**The location to be observed*/
	protected Location loc;

	/**
	 *
	 * @param cube Array with 2 Points defining the Cube, first being the "lower" point
	 * @param loc Location to be Monitored
	 * @param repeat false, if the Trigger should only be triggered once
	 * @param callback runnable to be called
	 * @param tm the TriggerManager for this trigger
	 */
	public AreaTrigger (Location[] cube, Location loc, boolean repeat, Runnable callback, TriggerManager tm) {
		super(repeat,callback,tm);
		this.cube = cube;
		this.loc = loc;
	}
	/**
	 * Sets the location to be observed
	 * @param loc new location to be observed
	 */
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
