package gt.general.trigger.callbacks;

import org.bukkit.Location;
import org.bukkit.material.Door;

public class DoorCloseCallback implements Runnable {

	private Location loc;
	
	/**public DoorCloseCallback(Door door) {
	 this.door = door;	
	}*/
	
	public DoorCloseCallback(Location door) {
	//	this.door = (Door) door.getBlock().getState().getData();
		this.loc = door;
	}
	
	@Override
	public void run() {
		Door door = (Door) loc.getBlock().getState().getData();
		door.setOpen(false);
		loc.getBlock().getState().setData(door);
	}

}