package gt.general.trigger;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * uses a minecraft
 * 
 * @author roman
 */
public class LeverTrigger extends RedstoneTrigger {

	private Block lever;
	
	/**
	 * @param lever the lever to be used as trigger
	 */
	public LeverTrigger(final Block lever) {
		super();
		
		this.lever = lever;
		setLabel("Lever_"+ hashCode());
	}
	
	
	@Override
	public void setup(final Map<String, Object> values, final World world) {
		super.setup(values, world);
		
		int x = (Integer) values.get("x");
		int y = (Integer) values.get("y");
		int z = (Integer) values.get("z");
		
		lever = world.getBlockAt(x, y, z);
		lever.setType(Material.LEVER);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		lever.setType(Material.AIR);
	}
	
	@Override
	public Map<String, Object> dump() {
		Map<String, Object> map = new HashMap<String,Object>();
		
		map.put("x", lever.getX());
		map.put("y", lever.getY());
		map.put("z", lever.getZ());

		return map;
	}

	@Override
	protected Block getBlock() {
		return lever;
	}

}
