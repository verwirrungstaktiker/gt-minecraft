/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic.trigger;

import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.exceptions.PersistenceException;
import gt.general.world.ObservableCustomBlock;
import gt.plugin.meta.CustomBlockType;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Lever;
import org.getspout.spoutapi.SpoutManager;

/**
 * uses a minecraft lever or stone button as trigger
 * 
 * @author roman
 */
public class LeverRedstoneTrigger extends RedstoneTrigger implements Listener {
	
	public static final String KEY_ORIENTATION = "orientation";
	
	private BlockFace orientation;
	
	/**
	 * @param trigger the lever to be used as trigger
	 * @param against against which block the player placed the trigger
	 */
	public LeverRedstoneTrigger(final Block trigger, final Block against) {
		super("lever_trigger", trigger);
		
		orientation = against.getFace(trigger);
		
		updateSignal();
	}
	
	/** to be used for persistence */
	public LeverRedstoneTrigger() {}
	
	@Override
	public void setup(final PersistenceMap values, final World world) throws PersistenceException {
		super.setup(values, world);
		
		orientation = values.get(KEY_ORIENTATION);

		Lever lever = (Lever) getBlock().getState().getData();
		lever.setFacingDirection(orientation);
		
		getBlock().setData(lever.getData());
		
		updateSignal();
	}
	
	@Override
	public PersistenceMap dump() {
		PersistenceMap map = super.dump();

		map.put(KEY_ORIENTATION, orientation);

		return map;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		SpoutManager
			.getMaterialManager()
			.removeBlockOverride(getBlock()
									.getRelative(orientation.getOppositeFace()));
	}
	
//	@EventHandler
//	@Override
//	public void onBlockRedstoneChange(final BlockRedstoneEvent event) {
//		if(isBlockRedstoneEventHere(event)) {
//			super.onBlockRedstoneChange(event);
//			
//			Block signalBlock = getBlock().getRelative(orientation.getOppositeFace());
//			
//			if(event.getNewCurrent() > 0) {
//				ObservableCustomBlock green = CustomBlockType.GREEN_SIGNAL.getCustomBlock();
//				SpoutManager.getMaterialManager().overrideBlock(signalBlock, green);
//			} else {
//				ObservableCustomBlock red = CustomBlockType.RED_SIGNAL.getCustomBlock();
//				SpoutManager.getMaterialManager().overrideBlock(signalBlock, red);
//			}
//		}
//	}
	
	@EventHandler
	@Override
	public void onPlayerInteract(final PlayerInteractEvent event) {
		if(getBlock().equals(event.getClickedBlock())) {
			super.onPlayerInteract(event);
			updateSignal();
		}
	}

	/**
	 * installs the signal behind the trigger
	 */
	private void updateSignal() {
		Block signalBlock = getBlock().getRelative(orientation.getOppositeFace());
		
		if(isActive()) {
			ObservableCustomBlock green = CustomBlockType.GREEN_SIGNAL.getCustomBlock();
			SpoutManager.getMaterialManager().overrideBlock(signalBlock, green);
		} else {
			ObservableCustomBlock red = CustomBlockType.RED_SIGNAL.getCustomBlock();
			SpoutManager.getMaterialManager().overrideBlock(signalBlock, red);
		}
	}
	
}
