package gt.general.trigger;

import gt.general.world.ObservableCustomBlock;

import java.util.HashMap;
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
	
	public final static ObservableCustomBlock GREEN_SIGNAL;
	public final static ObservableCustomBlock RED_SIGNAL;
	
	static {
		GREEN_SIGNAL = new ObservableCustomBlock("green_signal", "http://img703.imageshack.us/img703/5993/signalgreen.png", 16);
		RED_SIGNAL = new ObservableCustomBlock("red_signal", "http://img213.imageshack.us/img213/5175/signalred.png", 16);
	}
	
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
		SpoutManager.getMaterialManager().overrideBlock(signalBlock, RED_SIGNAL);
	}
	
	@EventHandler
	public void onBlockRedstoneChange(final BlockRedstoneEvent event) {
		if(isBlockRedstoneEventHere(event)) {
			super.onBlockRedstoneChange(event);
			
			Block signalBlock = getBlock().getRelative(orientation.getOppositeFace());
			
			if(event.getNewCurrent() > 0) {
				SpoutManager.getMaterialManager().overrideBlock(signalBlock, GREEN_SIGNAL);
			} else {
				SpoutManager.getMaterialManager().overrideBlock(signalBlock, RED_SIGNAL);
			}
		}
	}
}
