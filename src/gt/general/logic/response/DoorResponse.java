package gt.general.logic.response;


import java.util.HashSet;
import java.util.Set;

import gt.general.logic.TriggerEvent;
import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.exceptions.PersistenceException;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Door;

public class DoorResponse extends Response {

	//private static final String KEY_ORIENTATION = "orientation";
	private static final String KEY_MATERIAL = "material";
	private static final String KEY_LOCATION = "location";
	private static final String KEY_DATA_UPPER = "data_upper";
	private static final String KEY_DATA_LOWER = "data_lower";
	
	
	private Block lowerBlock, upperBlock;
	private Material material;
	//private BlockFace orientation;
	
	/**
	 * @param lowerBlock the lower block of a door
	 */
	public DoorResponse(final Block lowerBlock) {
		super("door_response");
		
		this.lowerBlock = lowerBlock;
		this.material = lowerBlock.getType();
		
		this.upperBlock = lowerBlock.getRelative(BlockFace.UP);
		// the orientation cannot be read here as it is not there yet
	}

	/**
	 * don't delete this anonymous constructor
	 */
	public DoorResponse() {}

	@Override
	public void setup(final PersistenceMap values, final World world) throws PersistenceException {

		lowerBlock = values.getBlock(KEY_LOCATION, world);
		upperBlock = lowerBlock.getRelative(BlockFace.UP);
		
		material = values.get(KEY_MATERIAL);
		
		int dataLower = values.get(KEY_DATA_LOWER);
		int dataUpper = values.get(KEY_DATA_UPPER);
		//orientation = values.get(KEY_ORIENTATION);

		lowerBlock.setType(material);
		upperBlock.setType(material);

		/*Door door = new Door(material);

		
		door.setTopHalf(true);
		upperBlock.setData(door.getData(),true);
		door.setTopHalf(false);
		door.setFacingDirection(orientation);		
		lowerBlock.setData(door.getData(),true);*/
		upperBlock.setData((byte) (dataUpper),true);
		lowerBlock.setData((byte) (dataLower),true);
	}
	
	@Override
	public void triggered(final TriggerEvent e) {
		
		Door door = (Door) lowerBlock.getState().getData();

		door.setOpen(e.isActive());
		// play the door toggle sound
		lowerBlock.getWorld().playEffect(lowerBlock.getLocation(), Effect.DOOR_TOGGLE, 25); // we can set the radius here
		
		lowerBlock.setData(door.getData(), true);
	}

	@Override
	public PersistenceMap dump() {
		PersistenceMap map = new PersistenceMap();
		map.put(KEY_LOCATION, lowerBlock);
		map.put(KEY_MATERIAL, material);
		
		// the orientation has to be read right before saving
		//Door door = (Door) lowerBlock.getState().getData();
		map.put(KEY_DATA_LOWER, lowerBlock.getData());
		map.put(KEY_DATA_UPPER, upperBlock.getData());
		
		return map;
	}

	@Override
	public void dispose() {
		lowerBlock.setType(Material.AIR);
		upperBlock.setType(Material.AIR);
	}

	@Override
	public Set<Block> getBlocks() {
		Set<Block> blocks = new HashSet<Block>();
		blocks.add(lowerBlock);
		blocks.add(upperBlock);
		
		return blocks;
	}

}
