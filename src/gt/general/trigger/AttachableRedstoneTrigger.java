package gt.general.trigger;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

/**
 * uses a minecraft lever or stone button as trigger
 * 
 * @author roman
 */
public class AttachableRedstoneTrigger extends RedstoneTrigger {

	private Block trigger;
	
	private Material material;
	
	/**
	 * @param trigger the lever to be used as trigger
	 */
	public AttachableRedstoneTrigger(final Block trigger) {
		super();
		
		this.trigger = trigger;
		material = trigger.getType();
		
		setLabel("attachable_trigger_"+ hashCode());
	}
	
	
	@Override
	public void setup(final Map<String, Object> values, final World world) {
		super.setup(values, world);
		
		int x = (Integer) values.get("x");
		int y = (Integer) values.get("y");
		int z = (Integer) values.get("z");
		
		material = (Material) values.get("material");
		
		trigger = world.getBlockAt(x, y, z);
		trigger.setType(material);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
		trigger.setType(Material.AIR);
	}
	
	@Override
	public Map<String, Object> dump() {
		Map<String, Object> map = new HashMap<String,Object>();
		
		map.put("x", trigger.getX());
		map.put("y", trigger.getY());
		map.put("z", trigger.getZ());
		
		map.put("material", material);

		return map;
	}

	@Override
	protected Block getBlock() {
		return trigger;
	}

}
