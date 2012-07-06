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
public class ButtonRedstoneTrigger extends AttachableRedstoneTrigger {
	
	private BlockFace orientation;
	
	/**
	 * @param trigger the lever to be used as trigger
	 */
	public ButtonRedstoneTrigger(final Block trigger) {
		super("button_trigger_", trigger);

	}
	
	public ButtonRedstoneTrigger() {
		
	}
	
	@Override
	public void setup(final Map<String, Object> values, final World world) {
		super.setup(values, world);

		orientation = (BlockFace) values.get("orientation");
		
		updateOrientation();

	}
	
	@Override
	public Map<String, Object> dump() {
		Map<String, Object> map = super.dump();//new HashMap<String,Object>();
		
		Button button = (Button) getBlock().getState().getData();
		orientation = button.getFacing();
		
		map.put("orientation", orientation);

		return map;
	}
	
	private void updateOrientation() {
		Button button = (Button) getBlock().getState().getData();
		button.setFacingDirection(orientation);
		
		getBlock().setData(button.getData());
	}

}
