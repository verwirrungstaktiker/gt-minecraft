package gt.general.logic.trigger;

import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.exceptions.PersistanceException;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.Button;

/**
 * uses a minecraft lever or stone button as trigger
 * 
 * @author roman
 */
public class ButtonRedstoneTrigger extends ResettingRedstoneTrigger {
	
	public static final String KEY_ORIENTATION = "orientation";
	
	private BlockFace orientation;
	
	/**
	 * @param trigger the lever to be used as trigger
	 * @param against the block to which the button is attached
	 */
	public ButtonRedstoneTrigger(final Block trigger, final Block against) {
		super("button_trigger", trigger);
		
		orientation = against.getFace(trigger);
	}
	
	/** to be used for persistance */
	public ButtonRedstoneTrigger() {}
	
	@Override
	public void setup(final PersistanceMap values, final World world) throws PersistanceException {
		super.setup(values, world);

		orientation = values.get(KEY_ORIENTATION);

		Button button = (Button) getBlock().getState().getData();
		button.setFacingDirection(orientation);
		
		getBlock().setData(button.getData());
		
	}
	
	@Override
	public PersistanceMap dump() {
		PersistanceMap map = super.dump();

		map.put(KEY_ORIENTATION, orientation);

		return map;
	}
}
