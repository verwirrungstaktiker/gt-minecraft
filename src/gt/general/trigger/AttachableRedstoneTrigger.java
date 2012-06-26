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
public abstract class AttachableRedstoneTrigger extends RedstoneTrigger {
	
	/**
	 * @param trigger the lever to be used as trigger
	 */
	public AttachableRedstoneTrigger(final String labelPrefix, final Block trigger) {
		super(labelPrefix, trigger);
	}
	
	public AttachableRedstoneTrigger() {
		
	}
	
	@Override
	public void setup(final Map<String, Object> values, final World world) {
		super.setup(values, world);

		material = (Material) values.get("material");
		
		trigger = blockFromCoordinates(values, world);
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
		

		map.putAll(coordinatesFromBlock(trigger));
		map.put("material", material);

		return map;
	}

}
