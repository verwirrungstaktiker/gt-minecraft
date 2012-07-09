package gt.general.trigger.response;


import gt.general.trigger.persistance.PersistanceMap;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockDisappearResponse extends BlockResponse {

	private boolean inverted = false;		//true if block appears on trigger
	
	/**
	 * do not delete this anonymous constructor
	 */
	public BlockDisappearResponse() {
		super();
	}
	
	/**
	 * @param block bukkit block
	 */
	public BlockDisappearResponse(final Block block) {
		super("appearable_block", block);
	}

	@Override
	public void setup(final PersistanceMap values, final World world) {
		super.setup(values, world);
		
		if(inverted) {
			getBlock().setType(Material.AIR);
		}
	}


	@Override
	public void triggered(final boolean active) {
		System.out.println("block triggered");
		Block block = getBlock();
		
		if(active ^ inverted) {
			// block disappear
			block.setType(Material.AIR);
		} else {
			// block appear
			block.setType(getMaterial());
		}
		// play a fancy effect
		block.getWorld().playEffect(block.getLocation(), Effect.POTION_BREAK, 10);
	}

	@Override
	public PersistanceMap dump() {
		PersistanceMap map = super.dump();
		
		map.put("invert", inverted);
		return map;
	}

	/**
	 * @return true if response is inverted
	 */
	public boolean isInverted() {
		return inverted;
	}

	/**
	 * @param inverted true if response is inverted
	 */
	public void setInverted(final boolean inverted) {
		this.inverted = inverted;
	}

}
