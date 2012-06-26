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
	
	/**
	 * @param pressurePlate the plate to be used as trigger
	 */
	public PressurePlateRedstoneTrigger(final Block pressurePlate) {
		super("pressure_plate", pressurePlate);
	}
	
	
	public PressurePlateRedstoneTrigger() {
		super();
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
