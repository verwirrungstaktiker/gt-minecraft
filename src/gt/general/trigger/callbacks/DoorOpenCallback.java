package gt.general.trigger.callbacks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Door;

public class DoorOpenCallback implements Runnable {

	private Location loc;
	
	/**public DoorOpenCallback(Door door) {
	 this.door = door;	
	}*/
	
	public DoorOpenCallback(Location door) {
		//this.door = (Door) 
		this.loc = door;
	}
	
	@Override
	public void run() {
		Door door = (Door)loc.getBlock().getState().getData(); 
		door.setOpen(true);
		loc.getBlock().getState().setData(door);
		loc.getBlock().setType(Material.AIR);
		if (door.isTopHalf()) {
			loc.setZ(loc.getZ()-1);
			loc.getBlock().setType(Material.AIR);			
		} else {
			loc.setZ(loc.getZ()+1);
			loc.getBlock().setType(Material.AIR);
		}
		
	}

}
