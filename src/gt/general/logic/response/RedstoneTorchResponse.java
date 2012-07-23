package gt.general.logic.response;


import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.exceptions.PersistanceException;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.RedstoneTorch;

public class RedstoneTorchResponse extends BlockResponse {

	private boolean inverted = false;		//true if response is inverted
	private BlockFace orientation;
	
	private static final String KEY_INVERTED = "inverted";
	private static final String KEY_ORIENTATION = "orientation";
	
	/**
	 * @param torchBlock material.RedstoneTorch
	 * @param against	 the block that holds the torch
	 */
	public RedstoneTorchResponse(final Block torchBlock, final Block against) {
		super("redstone_torch", torchBlock);
		
		orientation = against.getFace(torchBlock);
	}
	
	/**
	 * don't delete this anonymous constructor
	 */
	public RedstoneTorchResponse() {}

	@Override
	public void setup(final PersistanceMap values, final World world) throws PersistanceException {
		super.setup(values, world);
		
		inverted = values.get(KEY_INVERTED);
		orientation = values.get(KEY_ORIENTATION);
				
		if( !inverted ) {
			//TODO Maybe we can find another Material to represent a RedstoneTorch that's not glowing
			getBlock().setType(Material.AIR);
		} else {
			RedstoneTorch torch = (RedstoneTorch) getBlock().getState().getData();
			torch.setFacingDirection(orientation);
			getBlock().setData(torch.getData());
		}
	}


	@Override
	public void triggered(final boolean active) {
		System.out.println("block triggered");
		
		if(active ^ inverted) {
			// torch on
			getBlock().setType(getMaterial());
			//TODO do we need to set the orientation here?
		} else {
			// torch off
			getBlock().setType(Material.AIR);
		}
		// play a fancy effect
		getBlock().getWorld().playEffect(getBlock().getLocation(), Effect.ENDER_SIGNAL, 20);
	}

	@Override
	public PersistanceMap dump() {
		PersistanceMap map = super.dump();
		
		map.put(KEY_INVERTED, inverted);
		map.put(KEY_ORIENTATION, orientation);
		
		return map;
	}

	/**
	 * @return true if the logic of the torch is inverted
	 */
	public boolean isInverted() {
		return inverted;
	}

	/**
	 * @param inverted true if the logic of the torch is inverted (disappears on power)
	 */
	public void setInvert(final boolean inverted) {
		this.inverted = inverted;
	}

}
