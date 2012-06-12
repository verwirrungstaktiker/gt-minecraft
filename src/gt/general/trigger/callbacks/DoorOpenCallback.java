package gt.general.trigger.callbacks;

import org.bukkit.material.Door;

public class DoorOpenCallback implements Runnable {

	private Door door;
	
	public DoorOpenCallback(Door door) {
	 this.door = door;	
	}
	
	@Override
	public void run() {
		door.setOpen(true);
	}

}
