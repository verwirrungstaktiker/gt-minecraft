package gt.general.trigger.response;


import java.util.HashMap;
import java.util.Map;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class RedstoneTorchResponse extends AbstractResponse {

	private Block block;
	
//	private Material material;	//type of the block

	private boolean invert = false;		//true if response is inverted
	
	
	public RedstoneTorchResponse(Block torchBlock) {
		super("redstone_torch");
		this.block = torchBlock;
	}

	@Override
	public void setup(final Map<String, Object> values, final World world) {
		int x = (Integer) values.get("x");
		int y = (Integer) values.get("y");
		int z = (Integer) values.get("z");
		
		block = world.getBlockAt(x, y, z);
		
		if(invert) {
			block.setType(Material.REDSTONE_TORCH_ON);
		} else {
			block.setType(Material.AIR);
		}
	}


	@Override
	public void triggered(final boolean active) {
		System.out.println("block triggered");
		
		if(active ^ invert) {
			// torch on
			block.setType(Material.REDSTONE_TORCH_ON);
		} else {
			// torch off
			block.setType(Material.AIR);
		}
		// play a fancy effect
		block.getWorld().playEffect(block.getLocation(), Effect.ENDER_SIGNAL, 20);
	}
	

	@Override
	public void  dispose() {
		block.setType(Material.AIR);
	}

	@Override
	public Map<String, Object> dump() {
		Map<String, Object> map = new HashMap<String,Object>();
		
		map.put("x", block.getX());
		map.put("y", block.getY());
		map.put("z", block.getZ());
		
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
