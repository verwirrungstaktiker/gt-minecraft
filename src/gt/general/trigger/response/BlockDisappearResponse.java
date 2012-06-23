package gt.general.trigger.response;


import java.util.HashMap;
import java.util.Map;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockDisappearResponse extends Response {

	private Block block;
	
	private Material material;	//type of the block

	private boolean invert = false;		//true if block appears on trigger
	
	public BlockDisappearResponse() {
		super("appearable_block");		
	}
	
	public BlockDisappearResponse(Block block) {
		super("appearable_block");
		this.block = block;
		this.material = block.getType();
	}

	@Override
	public void setup(final Map<String, Object> values, final World world) {
		
		material = (Material) values.get("material");
		invert = (Boolean) values.get("invert");
		
		block = blockFromCoordinates(values, world);
		
		if(invert) {
			block.setType(Material.AIR);
		} else {
			block.setType(material);
		}
	}


	@Override
	public void triggered(final boolean active) {
		System.out.println("block triggered");
		
		if(active ^ invert) {
			// block disappear
			block.setType(Material.AIR);
		} else {
			// block appear
			block.setType(material);
		}
		// play a fancy effect
		block.getWorld().playEffect(block.getLocation(), Effect.POTION_BREAK, 10);
	}
	

	@Override
	public void  dispose() {
		block.setType(Material.AIR);
	}

	@Override
	public Map<String, Object> dump() {
		Map<String, Object> map = new HashMap<String,Object>();
		
		map.putAll(coordinatesFromPoint(block));
		map.put("material", material);
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
