package gt.general.trigger.response;


import java.util.Map;

import org.bukkit.Effect;
import org.bukkit.block.Block;
import org.bukkit.material.Door;


//TODO what about the orientation on setup?
public class DoorResponse extends BlockResponse {
	
	public DoorResponse(Block doorBlock) {
		super("door", doorBlock);
	}

	public DoorResponse() {
	}

	@Override
	public void triggered(final boolean active) {

		Block block = getBlock();
		
		Door door = (Door) block.getState().getData();

		door.setOpen(active);
		// play the door toggle sound
		block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 25); // we can set the radius here
		
		block.setData(door.getData(), true);
	}

	@Override
	public Map<String, Object> dump() {
		Map<String, Object> map = super.dump();
		
		Door door = (Door) getBlock().getState().getData();
		map.put("orientation", door.getFacing());
		
		return map;
	}

}
