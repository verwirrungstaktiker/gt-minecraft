package gt.general.logic.response;


import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.exceptions.PersistanceException;

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
		// the orientation cannot be read here as it is not there yet
	}

	/**
	 * don't delete this anonymous constructor
	 */
	public DoorResponse() {
	}

	@Override
	public void setup(final PersistanceMap values, final World world) throws PersistanceException {
		super.setup(values, world);
		
		orientation = values.get(KEY_ORIENTATION);
		
		Door door = (Door) getBlock().getState().getData();
		door.setFacingDirection(orientation);	
		
		getBlock().setData(door.getData());
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
		// the orientation has to be read right before saving
		Door door = (Door) getBlock().getState().getData();
		map.put(KEY_ORIENTATION, door.getFacing());
		
		return map;
	}

}
