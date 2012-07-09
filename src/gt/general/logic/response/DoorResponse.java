package gt.general.logic.response;


import gt.general.logic.persistance.PersistanceMap;

import org.bukkit.Effect;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Door;

public class DoorResponse extends BlockResponse {

	private static final String KEY_ORIENTATION = "orientation";
	private BlockFace orientation;
	
	/**
	 * @param doorBlock material.Door
	 */
	public DoorResponse(final Block doorBlock) {
		super("door", doorBlock);
	}

	/**
	 * don't delete this anonymous constructor
	 */
	public DoorResponse() {
	}

	@Override
	public void setup(final PersistanceMap values, final World world) {
		super.setup(values, world);
		
		orientation = values.get(KEY_ORIENTATION);
		
		Block block = getBlock();
		Door door = (Door) block.getState().getData();
		door.setFacingDirection(orientation);
		
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
	public PersistanceMap dump() {
		PersistanceMap map = super.dump();
		
		Door door = (Door) getBlock().getState().getData();
		map.put(KEY_ORIENTATION, door.getFacing());
		
		return map;
	}

}
