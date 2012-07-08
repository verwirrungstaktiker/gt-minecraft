package gt.general.trigger.response;


import java.util.Map;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockDisappearResponse extends BlockResponse {

	// TODO why has invert a different semantic as in RedstoneTorchResponse?
	private boolean invert = false;		//true if block appears on trigger
	
	public BlockDisappearResponse() {
		super();
	}
	
	public BlockDisappearResponse(Block block) {
		super("appearable_block", block);
	}

	@Override
	public void setup(final Map<String, Object> values, final World world) {
		super.setup(values, world);
		
		if(invert) {
			getBlock().setType(Material.AIR);
		}
	}


	@Override
	public void triggered(final boolean active) {
		System.out.println("block triggered");
		Block block = getBlock();
		
		if(active ^ invert) {
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
	public Map<String, Object> dump() {
		Map<String, Object> map = super.dump();
		
		map.put("invert", invert);
		return map;
	}

	public boolean isInvert() {
		return invert;
	}

	public void setInvert(boolean invert) {
		this.invert = invert;
	}

}
