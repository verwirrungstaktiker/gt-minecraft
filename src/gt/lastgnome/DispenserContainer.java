package gt.lastgnome;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.getspout.spout.block.SpoutCraftBlock;

import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.YamlSerializable;
import gt.general.logic.persistence.exceptions.PersistenceException;
import gt.general.world.ObservableCustomBlock;
import gt.plugin.meta.CustomBlockType;

/**
 * Holds all Dispensers in a Level
 * @author Roman
 *
 */
public class DispenserContainer extends YamlSerializable implements Listener {
	
	public static final String PERSISTANCE_FILE = "blocktool_dispenser.yml";
	public static final String KEY_DISPENSER = "dispenser";
	
	private Map<Block,BlocktoolDispenser> dispensers = new HashMap<Block,BlocktoolDispenser>();
	
	@Override
	public void setup(final PersistenceMap values, final World world)
			throws PersistenceException {
		ArrayList<Map<String,Integer>> dispenserList = values.get(KEY_DISPENSER);

		for(Map<String,Integer>  map : dispenserList) {
			int x = map.get("x");
			int y = map.get("y");
			int z = map.get("z");

			Block block = world.getBlockAt(x, y, z);
			BlocktoolDispenser dispenser = new BlocktoolDispenser(block);
			
			dispensers.put(block,dispenser);
		}
	}
	
	/**
	 * @return all dispensers
	 */
	public Collection<BlocktoolDispenser> getDispensers() {
		return dispensers.values();
	}

	@Override
	public PersistenceMap dump() {
		PersistenceMap map = new PersistenceMap();
		ArrayList<Map<String, Integer>> coordinates = new ArrayList<Map<String, Integer>>();
		
		for(BlocktoolDispenser dispenser : getDispensers()) {
			Map<String, Integer> dispenserCoordinates = new HashMap<String, Integer>();
			Block block = dispenser.getBlock();
			
			dispenserCoordinates.put("x",block.getX());
			dispenserCoordinates.put("y",block.getY());
			dispenserCoordinates.put("z",block.getZ());
			
			coordinates.add(dispenserCoordinates);
		}
		
		map.put(KEY_DISPENSER, coordinates);
		
		return map;
	}

	@Override
	public void dispose() {
		// unregister all block observers
		for(BlocktoolDispenser dispenser : getDispensers()) {
			dispenser.dispose();
		}
		dispensers.clear();
	}

	@Override
	public Set<Block> getBlocks() {
		return new HashSet<Block>();
	}

	/**
	 * handle building of BlockToolDispensers
	 * @param event block place
	 */
	@EventHandler
	public void onBlockPlace(final BlockPlaceEvent event) {

		Block block = event.getBlock();
		
		if(block instanceof SpoutCraftBlock) {
			SpoutCraftBlock sBlock = (SpoutCraftBlock) block;
			// custom blocks
			if(sBlock.getCustomBlock() instanceof ObservableCustomBlock) {
				ObservableCustomBlock oBlock = (ObservableCustomBlock) sBlock.getCustomBlock();
				
				if(oBlock.getCustomBlockType()==CustomBlockType.BLOCKTOOL_DISPENSER) {
					
					BlocktoolDispenser dispenser = new BlocktoolDispenser(block);
					dispensers.put(block,dispenser);
					
					event.getPlayer().sendMessage(ChatColor.GREEN + "BuildTool Dispenser placed.");
				}
			}
		}
	}
	
	/**
	 * handle destroying of BlockToolDispensers
	 * @param event block break
	 */
	@EventHandler
	public void onBlockBreak(final BlockBreakEvent event) {

		Block block = event.getBlock();
		BlocktoolDispenser dispenser = dispensers.remove(block);
		
		if(dispenser!=null) {
			dispenser.dispose();
			
			event.getPlayer().sendMessage(ChatColor.YELLOW + "BuildTool Dispenser destroyed.");
		}
	}
}
