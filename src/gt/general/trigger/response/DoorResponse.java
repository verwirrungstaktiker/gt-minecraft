package gt.general.trigger.response;


import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Door;
import org.getspout.spoutapi.SpoutManager;

public class DoorResponse implements Response {

	private Block doorBlock;
	private String label;
	
	
	public DoorResponse(Block doorBlock) {
		this.doorBlock = doorBlock;
	}
	
/**	
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
*/
	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void setLabel(final String label) {
		this.label = label;
		
	}

	@Override
	public void setup(final Map<String, Object> values, final World world) {
		int x = (Integer) values.get("x");
		int y = (Integer) values.get("y");
		int z = (Integer) values.get("z");
		
		doorBlock = world.getBlockAt(x, y, z);
		doorBlock.setType(Material.IRON_DOOR);
	}


	@Override
	public void triggered(final boolean active) {
		Door door = (Door) doorBlock.getState().getData();
		Block otherhalf;
		
		if (door.isTopHalf()) {
			otherhalf = doorBlock.getRelative(BlockFace.DOWN);			
		} else {
			otherhalf = doorBlock.getRelative(BlockFace.UP);
		}		
		if(active) {
			door.setOpen(true);
		} else {
			door.setOpen(false);
		}
		doorBlock.setData(door.getData(), true);
		door.setTopHalf(!door.isTopHalf());
		otherhalf.setData(door.getData(), true);

	}
	

	@Override
	public void  dispose() {
		doorBlock.setType(Material.AIR);
	}

	@Override
	public Map<String, Object> dump() {
		Map<String, Object> map = new HashMap<String,Object>();
		
		map.put("x", doorBlock.getX());
		map.put("y", doorBlock.getY());
		map.put("z", doorBlock.getZ());

		Door door = (Door) doorBlock.getState().getData();
		map.put("orientation", door.getFacing());
		
		return map;
	}

}
