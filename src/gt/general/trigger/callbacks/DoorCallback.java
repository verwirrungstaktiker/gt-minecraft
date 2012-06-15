package gt.general.trigger.callbacks;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Door;

public class DoorCallback implements DeviceCallback{

	private Block doorblock;
	
	public DoorCallback(Block doorblock) {
		this.doorblock = doorblock;
	}
	
	public DoorCallback(Location doorloc) {
		this.doorblock = doorloc.getBlock();
	}
	
	@Override
	public void onDisable() {
		Door door = (Door) doorblock.getState().getData();
		Block otherhalf;
		
		if (door.isTopHalf()) {
			otherhalf = doorblock.getRelative(BlockFace.DOWN);			
		} else {
			otherhalf = doorblock.getRelative(BlockFace.UP);
		}
		
		door.setOpen(false);
		doorblock.setData(door.getData(), true);
		door.setTopHalf(!door.isTopHalf());
		otherhalf.setData(door.getData(), true);
		//doorblock.getWorld().pla	
	}

	@Override
	public void onEnable() {
		Door door = (Door) doorblock.getState().getData();
		Block otherhalf;
		
		if (door.isTopHalf()) {
			otherhalf = doorblock.getRelative(BlockFace.DOWN);			
		} else {
			otherhalf = doorblock.getRelative(BlockFace.UP);
		}
		
		door.setOpen(true);
		doorblock.setData(door.getData(), true);
		door.setTopHalf(!door.isTopHalf());
		otherhalf.setData(door.getData(), true);
		
	}

}
