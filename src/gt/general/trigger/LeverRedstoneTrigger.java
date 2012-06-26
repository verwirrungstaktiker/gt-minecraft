package gt.general.trigger;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Button;
import org.bukkit.material.Lever;

/**
 * uses a minecraft lever or stone button as trigger
 * 
 * @author roman
 */
public class LeverRedstoneTrigger extends AttachableRedstoneTrigger {
	
	private BlockFace orientation;
	
	/**
	 * @param trigger the lever to be used as trigger
	 */
	public LeverRedstoneTrigger(final Block trigger) {
		super("lever_trigger_", trigger);

	}
	
	public LeverRedstoneTrigger() {
		
	}
	
	@Override
	public void setup(final Map<String, Object> values, final World world) {
		super.setup(values, world);
		
		orientation = (BlockFace) values.get("orientation");

		updateOrientation();
		//
		System.out.println("load:" + orientation);
	}
	
	@Override
	public Map<String, Object> dump() {
		Map<String, Object> map = new HashMap<String,Object>();

		map.putAll(coordinatesFromBlock(trigger));
		map.put("material", material);
		
		Lever lever = (Lever) trigger.getState().getData();
		orientation = lever.getFacing();
		
		map.put("orientation", orientation);
		//
		System.out.println("dump:" + orientation);

		return map;
	}
	
	private void updateOrientation() {
		Lever lever = (Lever) trigger.getState().getData();
		lever.setFacingDirection(orientation);
		
		trigger.setData(lever.getData());
	}

}
