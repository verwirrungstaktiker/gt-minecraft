package gt.general.trigger;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Uses a stone pressure plate as trigger
 * 
 * @author Sebastian Fahnenschreiber
 */
public class PressurePlateTrigger extends RedstoneTrigger {

	private Block pressurePlate;
	
	/**
	 * @param pressurePlate the plate to be used as trigger
	 */
	public PressurePlateTrigger(final Block pressurePlate) {
		super();
		
		this.pressurePlate = pressurePlate;
		setLabel("PressurePlate_"+ hashCode());
	}
	
	
	@Override
	public void setup(final Map<String, Object> values, final World world) {
		super.setup(values, world);
		
		int x = (Integer) values.get("x");
		int y = (Integer) values.get("y");
		int z = (Integer) values.get("z");
		
		pressurePlate = world.getBlockAt(x, y, z);
		pressurePlate.setType(Material.STONE_PLATE);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		pressurePlate.setType(Material.AIR);
	}
	
	@Override
	public Map<String, Object> dump() {
		Map<String, Object> map = new HashMap<String,Object>();
		
		map.put("x", pressurePlate.getX());
		map.put("y", pressurePlate.getY());
		map.put("z", pressurePlate.getZ());

		return map;
	}

	@Override
	protected Block getBlock() {
		return pressurePlate;
	}

}
