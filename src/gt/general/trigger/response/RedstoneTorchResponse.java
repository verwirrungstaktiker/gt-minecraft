package gt.general.trigger.response;


import java.util.Map;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class RedstoneTorchResponse extends BlockResponse {

	private boolean invert = false;		//true if response is inverted
	
	private final static String KEY_INVERT = "invert";
	
	public RedstoneTorchResponse(final Block torchBlock) {
		super("redstone_torch", torchBlock);
	}
	
	public RedstoneTorchResponse() {}

	@Override
	public void setup(final Map<String, Object> values, final World world) {
		super.setup(values, world);
		invert = (Boolean) values.get(KEY_INVERT);
		
		if( !invert ) {
			//TODO: Maybe we can find another Material to represent a RedstoneTorch that's not glowing
			getBlock().setType(Material.AIR);
		}
	}


	@Override
	public void triggered(final boolean active) {
		System.out.println("block triggered");
		
		if(active ^ invert) {
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
	public Map<String, Object> dump() {
		Map<String, Object> map = super.dump();
		
		map.put(KEY_INVERT, invert);
		return map;
	}

	public boolean isInvert() {
		return invert;
	}

	public void setInvert(boolean invert) {
		this.invert = invert;
	}

}
