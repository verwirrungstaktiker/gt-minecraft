package gt.general.trigger.callbacks;

import org.bukkit.material.Door;

public class DoorCloseCallback implements Runnable {

	private Door door;
	
	public DoorCloseCallback(Door door) {
	 this.door = door;	
	}
	
	@Override
	public void run() {
		door.setOpen(false);
	}

}