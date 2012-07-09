package gt.general.logic.trigger;

import gt.general.logic.persistance.PersistanceMap;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Button;

/**
 * uses a minecraft lever or stone button as trigger
 * 
 * @author roman
 */
public class ButtonRedstoneTrigger extends RedstoneTrigger {
	
	public static final String KEY_ORIENTATION = "orientation";
	
	private BlockFace orientation;
	
	/**
	 * @param trigger the lever to be used as trigger
	 */
	public ButtonRedstoneTrigger(final Block trigger) {
		super("button_trigger_", trigger);

	}
	
	/** to be used for persistance */
	public ButtonRedstoneTrigger() {}
	
	@Override
	public void setup(final PersistanceMap values, final World world) {
		super.setup(values, world);

		orientation = values.get(KEY_ORIENTATION);

		Button button = (Button) getBlock().getState().getData();
		button.setFacingDirection(orientation);
		
		getBlock().setData(button.getData());
		
	}
	
	@Override
	public PersistanceMap dump() {
		PersistanceMap map = super.dump();
		
		Button button = (Button) getBlock().getState().getData();
		orientation = button.getFacing();
		
		map.put(KEY_ORIENTATION, orientation);

		return map;
	}
}
