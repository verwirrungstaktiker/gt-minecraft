package gt.general.logic.response;


import gt.general.logic.persistance.PersistanceMap;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class RedstoneTorchResponse extends BlockResponse {

	private boolean inverted = false;		//true if response is inverted
	
	private static final String KEY_INVERTED = "inverted";
	
	/**
	 * @param torchBlock material.RedstoneTorch
	 */
	public RedstoneTorchResponse(final Block torchBlock) {
		super("redstone_torch", torchBlock);
	}
	
	/**
	 * don't delete this anonymous constructor
	 */
	public RedstoneTorchResponse() {}

	@Override
	public void setup(final PersistanceMap values, final World world) {
		super.setup(values, world);
		inverted = values.get(KEY_INVERTED);
		
		if( !inverted ) {
			//TODO Maybe we can find another Material to represent a RedstoneTorch that's not glowing
			getBlock().setType(Material.AIR);
		}
	}


	@Override
	public void triggered(final boolean active) {
		System.out.println("block triggered");
		
		if(active ^ inverted) {
			// torch on
			getBlock().setType(getMaterial());
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
		return map;
	}

	/**
	 * @return true if the logic of the torch is inverted
	 */
	public boolean isInverted() {
		return inverted;
	}

	/**
	 * @param inverted true if the logich of the torch is inverted
	 */
	public void setInvert(final boolean inverted) {
		this.inverted = inverted;
	}

}
