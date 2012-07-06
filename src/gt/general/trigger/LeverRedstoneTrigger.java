package gt.general.trigger;

import gt.general.world.ObservableCustomBlock;
import gt.plugin.meta.CustomBlockType;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.material.Lever;
import org.getspout.spoutapi.SpoutManager;

/**
 * uses a minecraft lever or stone button as trigger
 * 
 * @author roman
 */
public class LeverRedstoneTrigger extends AttachableRedstoneTrigger implements Listener {
	
	private BlockFace orientation;
	
	/**
	 * @param trigger the lever to be used as trigger
	 */
	public LeverRedstoneTrigger(Block trigger, Block against) {
		super("lever_trigger_", trigger);
		
		orientation = against.getFace(trigger);
		
		installSignal();
	}
	
	public LeverRedstoneTrigger() {}
	
	@Override
	public void setup(final Map<String, Object> values, final World world) {
		super.setup(values, world);
		
		orientation = (BlockFace) values.get("orientation");

		updateOrientation();
		installSignal();
		//
		System.out.println("load:" + orientation);
	}
	
	@Override
	public Map<String, Object> dump() {
		Map<String, Object> map = super.dump();//new HashMap<String,Object>();
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

	
	@Override
	public void dispose() {
		super.dispose();
		getBlock().getRelative(orientation.getOppositeFace()).setType(Material.AIR);
	}
	
	
	private void installSignal() {		
		Block signalBlock = getBlock().getRelative(orientation.getOppositeFace());
		ObservableCustomBlock red = CustomBlockType.RED_SIGNAL.getCustomBlock();
		
		SpoutManager.getMaterialManager().overrideBlock(signalBlock, red);
	}
	
	@EventHandler
	public void onBlockRedstoneChange(final BlockRedstoneEvent event) {
		if(isBlockRedstoneEventHere(event)) {
			super.onBlockRedstoneChange(event);
			
			Block signalBlock = getBlock().getRelative(orientation.getOppositeFace());
			
			if(event.getNewCurrent() > 0) {
				ObservableCustomBlock green = CustomBlockType.GREEN_SIGNAL.getCustomBlock();
				SpoutManager.getMaterialManager().overrideBlock(signalBlock, green);
			} else {
				ObservableCustomBlock red = CustomBlockType.RED_SIGNAL.getCustomBlock();
				SpoutManager.getMaterialManager().overrideBlock(signalBlock, red);
			}
		}
	}
}
