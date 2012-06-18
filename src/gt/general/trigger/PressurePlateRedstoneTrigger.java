package gt.general.trigger;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * Uses a pressure block as trigger (wood or stone)
 * 
 * @author Sebastian Fahnenschreiber
 */
public class PressurePlateRedstoneTrigger extends RedstoneTrigger {

	private Block pressureBlock;
	
	private Material material;
	
	/**
	 * @param pressurePlate the plate to be used as trigger
	 */
	public PressurePlateRedstoneTrigger(final Block pressurePlate) {
		super();
		
		this.pressureBlock = pressurePlate;
		this.material = pressurePlate.getType();
		
		setLabel("PressurePlate_"+ hashCode());
	}
	
	
	@Override
	public void setup(final Map<String, Object> values, final World world) {
		super.setup(values, world);
		
		int x = (Integer) values.get("x");
		int y = (Integer) values.get("y");
		int z = (Integer) values.get("z");
		
		material = (Material) values.get("material");
		
		pressureBlock = world.getBlockAt(x, y, z);
		pressureBlock.setType(material);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		pressureBlock.setType(Material.AIR);
	}
	
	@Override
	public Map<String, Object> dump() {
		Map<String, Object> map = new HashMap<String,Object>();
		
		map.put("x", pressureBlock.getX());
		map.put("y", pressureBlock.getY());
		map.put("z", pressureBlock.getZ());
		
		map.put("material", material);

		return map;
	}

	@Override
	protected Block getBlock() {
		return pressureBlock;
	}

}
