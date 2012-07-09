package gt.general.trigger.persistance;

import static com.google.common.collect.Maps.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;


public class PersistanceMap {

	public static final String KEY_X_COORDINATE = "x";
	public static final String KEY_Y_COORDINATE = "y";
	public static final String KEY_Z_COORDINATE = "z";
	
	private Map<String, Object> map;

	public PersistanceMap() {
		map = newHashMap();
	}
	public PersistanceMap(final Map<String, Object> map) {
		this.map = map;
	}
	
	public int getInt(final String key) {
		return ((Integer)map.get(key)).intValue();
	}
	
	public double getDouble(final String key) {
		return ((Double)map.get(key)).doubleValue();
	}
	
	public boolean getBoolean(final String key) {
		return ((Boolean)map.get(key)).booleanValue();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(final String key) {
		return (T) map.get(key);
	}
	
	
	public Block getBlock(final String key, final World world) {
		Map<String, Object> coords = get(key);
		return blockFromCoords(coords, world);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Block> getBlocks(final String key, final World world) {
		List<Map<String, Object>> values = (List<Map<String, Object>>) map.get(key);
		List<Block> blocks = new ArrayList<Block>();
		
		for(Map<String, Object> coords : values) {
			blocks.add(blockFromCoords(coords, world));
		}
		
		return blocks;
	}
		
	private Block blockFromCoords( Map<String, Object> coords, final World world) {
		return world.getBlockAt((Integer) coords.get(KEY_X_COORDINATE),
								(Integer) coords.get(KEY_Y_COORDINATE),
								(Integer) coords.get(KEY_Z_COORDINATE));
	}
	
	
	
	
	public Location getLocation(final String key, final World world) {
		Map<String, Object> coords = get(key);
		return new Location(world,
							(Double) coords.get(KEY_X_COORDINATE),
							(Double) coords.get(KEY_Y_COORDINATE),
							(Double) coords.get(KEY_Z_COORDINATE));	
	}
	
	
	public void put(final String key, final Object value) {
		map.put(key, value);
	}

	public void put(final String key, final Block block) {
		map.put(key, coordsFromBlock(block));
		
	}
	
	public void put(final String key, final Collection<Block> block) {
		List<Object> blocks = new ArrayList<Object>();
		
		for(Block b : block) {
			blocks.add(coordsFromBlock(b));
		}
		
		map.put(key, blocks);
	}
	
	private Map<String, Object> coordsFromBlock(final Block block) {
		Map<String, Object> coords = newHashMap();
		
		coords.put(KEY_X_COORDINATE, block.getX());
		coords.put(KEY_Y_COORDINATE, block.getY());
		coords.put(KEY_Z_COORDINATE, block.getZ());
		
		return coords;
	}
	
	public void put(final String key, final Location location) {
		Map<String, Object> coords = newHashMap();
		
		coords.put(KEY_X_COORDINATE, location.getX());
		coords.put(KEY_Y_COORDINATE, location.getY());
		coords.put(KEY_Z_COORDINATE, location.getZ());
		
		map.put(key, coords);
	}
}
