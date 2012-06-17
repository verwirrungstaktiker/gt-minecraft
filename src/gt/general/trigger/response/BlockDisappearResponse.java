package gt.general.trigger.response;


import java.util.HashMap;
import java.util.Map;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class BlockDisappearResponse implements Response {

	private Block block;
	private String label;
	
	private Material material;	//type of the block

	private boolean invert = false;		//true if block appears on trigger
	
	
	public BlockDisappearResponse(Block doorBlock) {
		this.block = doorBlock;
		this.material = doorBlock.getType();
	}

	@Override
	public String getLabel() {
		return label;
	}

	@Override
	public void setLabel(final String label) {
		this.label = label;
		
	}

	@Override
	public void setup(final Map<String, Object> values, final World world) {
		int x = (Integer) values.get("x");
		int y = (Integer) values.get("y");
		int z = (Integer) values.get("z");
		
		material = (Material) values.get("material");
		boolean invert = (Boolean) values.get("invert");
		
		block = world.getBlockAt(x, y, z);
		
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
		
		map.put("x", block.getX());
		map.put("y", block.getY());
		map.put("z", block.getZ());
		
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
