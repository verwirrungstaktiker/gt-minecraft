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
		super("pressure_plate");
		
		this.pressureBlock = pressurePlate;
		this.material = pressurePlate.getType();
	}
	
	
	public PressurePlateRedstoneTrigger() {
		super();
	}


	@Override
	public void setup(final Map<String, Object> values, final World world) {
		super.setup(values, world);
		
		material = (Material) values.get("material");
		
		pressureBlock = blockFromCoordinates(values, world);
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
		
		map.putAll(coordinatesFromPoint(pressureBlock));
		map.put("material", material);
		
		return map;
	}

	@Override
	protected Block getBlock() {
		return pressureBlock;
	}

}
