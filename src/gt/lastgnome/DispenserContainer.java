package gt.lastgnome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.block.Block;

import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.YamlSerializable;
import gt.general.logic.persistence.exceptions.PersistenceException;

public class DispenserContainer extends YamlSerializable {
	
	public static final String FILENAME = "blocktool_dispenser.yml";
	public static final String KEY_DISPENSER = "dispenser";
	
	private Set<BlocktoolDispenser> dispensers = new HashSet<BlocktoolDispenser>();
	
	@Override
	public void setup(final PersistenceMap values, final World world)
			throws PersistenceException {

		ArrayList<Map<String,Integer>> dispenserList = values.get(KEY_DISPENSER);

		for(Map<String,Integer>  map : dispenserList) {
			int x = map.get("x");
			int y = map.get("y");
			int z = map.get("z");

			BlocktoolDispenser dispenser = new BlocktoolDispenser(world.getBlockAt(x, y, z));
			
			dispensers.add(dispenser);
		}
	}
	
	public Set<BlocktoolDispenser> getDispensers() {
		return dispensers;
	}

	@Override
	public PersistenceMap dump() {
		PersistenceMap map = new PersistenceMap();
		ArrayList<Map<String, Integer>> coordinates = new ArrayList<Map<String, Integer>>();
		
		for(BlocktoolDispenser dispenser : dispensers) {
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
		for(BlocktoolDispenser dispenser : dispensers) {
			dispenser.dispose();
		}
	}

	@Override
	public Set<Block> getBlocks() {
		return new HashSet<Block>();
	}
}
