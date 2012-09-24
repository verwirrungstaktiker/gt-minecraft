/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
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

/**
 * Holds all Dispensers in a Level
 * @author Roman
 *
 */
public class DispenserContainer extends YamlSerializable implements Listener {

	public static final String PERSISTANCE_FILE = "dispenser.yml";
	public static final String KEY_DISPENSER = "dispenser";
	public static final String KEY_ALL_DISPENSERS = "all_dispensers";
	public static final String KEY_DISPENSED_ITEM_TYPE = "type";
	
	private Map<Block,Dispenser> dispensers = new HashMap<Block,Dispenser>();
	
	@Override
	public void setup(final PersistenceMap values, final World world)
			throws PersistenceException {
		ArrayList<Map<String,Object>> dispenserList = values.get(KEY_DISPENSER);

		for(Map<String,Object>  map : dispenserList) {
			int x = (Integer) map.get("x");
			int y = (Integer) map.get("y");
			int z = (Integer) map.get("z");
			
			DispenserItem type = DispenserItem.valueOf((String) map.get(KEY_DISPENSED_ITEM_TYPE));

			Block block = world.getBlockAt(x, y, z);
			Dispenser dispenser;
			
			switch(type) {
			case BLOCKTOOL: 
				dispenser = new BlocktoolDispenser(block);
				break;
			case BLUE_KEY:
				dispenser = new KeyDispenser(block, DispenserItem.BLUE_KEY);
				break;
			case RED_KEY:
				dispenser = new KeyDispenser(block, DispenserItem.RED_KEY);
				break;
			case GREEN_KEY:
				dispenser = new KeyDispenser(block, DispenserItem.GREEN_KEY);
				break;
			case YELLOW_KEY:
				dispenser = new KeyDispenser(block, DispenserItem.YELLOW_KEY);
				break;
			default:
				dispenser = new BlocktoolDispenser(block);
				System.out.println("Something went wrong when reading the DispenserItem type in DispenserContainer." +
						" Made a BlockToolDispenser");
			
			}
			
			
			dispensers.put(block,dispenser);
		}
	}
	
	/**
	 * @return all dispensers
	 */
	public Collection<Dispenser> getDispensers() {
		return dispensers.values();
	}

	@Override
	public PersistenceMap dump() {
		PersistenceMap map = new PersistenceMap();
		ArrayList<Map<String, Object>> coordinates = new ArrayList<Map<String, Object>>();
		
		for(Dispenser dispenser : getDispensers()) {
			Map<String, Object> dispenserCoordinates = new HashMap<String, Object>();
			Block block = dispenser.getBlock();
			
			dispenserCoordinates.put("x",block.getX());
			dispenserCoordinates.put("y",block.getY());
			dispenserCoordinates.put("z",block.getZ());
			
			dispenserCoordinates.put(KEY_DISPENSED_ITEM_TYPE,dispenser.getItemType().toString());
			
			coordinates.add(dispenserCoordinates);
		}
		
		map.put(KEY_DISPENSER, coordinates);
		
		return map;
	}

	@Override
	public void dispose() {
		// unregister all block observers
		for(Dispenser dispenser : getDispensers()) {
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
				
				Dispenser dispenser = null;
				
				switch(oBlock.getCustomBlockType()) {
				case BLOCKTOOL_DISPENSER:
					dispenser = new BlocktoolDispenser(block);
					break;
				case BLUE_KEY_DISPENSER:
					dispenser = new KeyDispenser(block, DispenserItem.BLUE_KEY);
					break;
				case RED_KEY_DISPENSER:
					dispenser = new KeyDispenser(block, DispenserItem.RED_KEY);
					break;
				case GREEN_KEY_DISPENSER:
					dispenser = new KeyDispenser(block, DispenserItem.GREEN_KEY);
					break;
				case YELLOW_KEY_DISPENSER:
					dispenser = new KeyDispenser(block, DispenserItem.YELLOW_KEY);
					break;
				default:
					return;
				}
				dispensers.put(block,dispenser);
				
				event.getPlayer().sendMessage(ChatColor.GREEN + "" + dispenser.getItemType() + " placed.");
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
		Dispenser dispenser = dispensers.remove(block);
		
		if(dispenser!=null) {
			dispenser.dispose();
			
			event.getPlayer().sendMessage(ChatColor.YELLOW + "BuildTool Dispenser destroyed.");
		}
	}
}
