package gt.general.logic.persistance;

import static com.google.common.collect.Maps.newHashMap;

import gt.general.logic.persistance.exceptions.PersistanceException;

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

	/**
	 * creates a new PersistanceMap
	 */
	public PersistanceMap() {
		map = newHashMap();
	}
	
	/**
	 * creates a new PersistanceMap
	 * @param map usual Map that shall be integrated
	 */
	public PersistanceMap(final Map<String, Object> map) {
		this.map = map;
	}
	
	/**
	 * @param key name of the field
	 * @return value corresponding to the key
	 */
	public int getInt(final String key) {
		Integer result = (Integer)map.get(key);
		if (result == null) {
			
		}
		return result.intValue();
	}
	
	/**
	 * @param key name of the field
	 * @return value corresponding to the key
	 */
	public double getDouble(final String key) {
		Double result = (Double)map.get(key);
		if (result == null) {
			
		}
		return result.doubleValue();
	}
	
	/**
	 * @param key name of the field
	 * @return value corresponding to the key
	 */
	public boolean getBoolean(final String key) {
		Boolean result = (Boolean)map.get(key);
		if (result == null) {
			
		}
		return result.booleanValue();
	}
	
	/**
	 * can be used for unknown type
	 * @param key name of the field
	 * @param <T> type of the value that shall be returned
	 * @return value corresponding to the key
	 * @throws PersistanceException 
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(final String key) throws PersistanceException {
		T result = (T) map.get(key);
		if (result == null) {
			throw new PersistanceException(key);
		}
		return result;
	}
	
	/**
	 * gets a bukkit block
	 * @param key name of the field
	 * @param world world that holds the block
	 * @return bukkit block to the key
	 * @throws PersistanceException 
	 */
	public Block getBlock(final String key, final World world) throws PersistanceException {
		Map<String, Object> coords = get(key);
		return blockFromCoords(coords, world);
	}
	
	/**
	 * get a collection of bukkit blocks
	 * @param key name of the field
	 * @param world world that holds the block
	 * @return all blocks that correspond to the key
	 * @throws PersistanceException 
	 */
	@SuppressWarnings("unchecked")
	public Collection<Block> getBlocks(final String key, final World world) throws PersistanceException {
		List<Map<String, Object>> values = (List<Map<String, Object>>) map.get(key);
		List<Block> blocks = new ArrayList<Block>();
		
		for(Map<String, Object> coords : values) {
			blocks.add(blockFromCoords(coords, world));
		}
		
		return blocks;
	}
	
	/**
	 * get the block at specific coordinates
	 * @param coords coordinates of the block
	 * @param world world that holds the block
	 * @return bukkit block at the specified location
	 * @throws PersistanceException 
	 */
	private Block blockFromCoords( final Map<String, Object> coords, final World world) throws PersistanceException {
		Integer x = (Integer) coords.get(KEY_X_COORDINATE);
		if (x == null) {
			throw new PersistanceException(KEY_X_COORDINATE);
		}
		Integer y = (Integer) coords.get(KEY_Y_COORDINATE);
		if (y == null) {
			throw new PersistanceException(KEY_Y_COORDINATE);
		}
		Integer z = (Integer) coords.get(KEY_Z_COORDINATE);
		if (z == null) {
			throw new PersistanceException(KEY_Z_COORDINATE);
		}
		return world.getBlockAt(x,y,z);
	}
	
	/**
	 * read a location 
	 * @param key name of the field
	 * @param world world that holds the location
	 * @return location that corresponds to a key
	 * @throws PersistanceException 
	 */
	public Location getLocation(final String key, final World world) throws PersistanceException {
		Map<String, Object> coords = get(key);
		return new Location(world,
							(Double) coords.get(KEY_X_COORDINATE),
							(Double) coords.get(KEY_Y_COORDINATE),
							(Double) coords.get(KEY_Z_COORDINATE));	
	}
	
	/**
	 * save an unspecified Object
	 * @param key name of the corresponding field
	 * @param value the Object that is saved
	 */
	public void put(final String key, final Object value) {
		if (value == null) {
			//TODO throw Exception
		}
		map.put(key, value);
	}

	/**
	 * save a block
	 * @param key name of the corresponding field
	 * @param block the Block that is saved
	 */
	public void put(final String key, final Block block) {
		if (block == null) {
			//TODO throw Exception
		}		
		map.put(key, coordsFromBlock(block));
		
	}
	
	/**
	 * Save multiple blocks at the same time
	 * @param key name of the corresponding field
	 * @param blocks Blocks that are saved
	 */
	public void put(final String key, final Collection<Block> blocks) {
		List<Object> blocksToSave = new ArrayList<Object>();
		
		for(Block b : blocks) {
			blocksToSave.add(coordsFromBlock(b));
		}
		
		map.put(key, blocksToSave);
	}
	
	/**
	 * get the location of a block
	 * @param block a bukkit block
	 * @return the coordinates of the block in a map
	 */
	private Map<String, Object> coordsFromBlock(final Block block) {
		Map<String, Object> coords = newHashMap();
		
		coords.put(KEY_X_COORDINATE, block.getX());
		coords.put(KEY_Y_COORDINATE, block.getY());
		coords.put(KEY_Z_COORDINATE, block.getZ());
		
		return coords;
	}
	
	/**
	 * save a location
	 * @param key name of the corresponding field
	 * @param location location to be saved
	 */
	public void put(final String key, final Location location) {
		Map<String, Object> coords = newHashMap();
		
		coords.put(KEY_X_COORDINATE, location.getX());
		coords.put(KEY_Y_COORDINATE, location.getY());
		coords.put(KEY_Z_COORDINATE, location.getZ());
		
		map.put(key, coords);
	}
	
	/**
	 * delete an Object of the map
	 * @param key name of the field that shall be deleted
	 * @param <T> type of the Object that is deleted
	 * @return the removed Object
	 */
	@SuppressWarnings("unchecked")
	public <T> T remove(final String key) {
		return (T) map.remove(key);
	}
	
	/**
	 * @return all saved data
	 */
	public Map<String, Object> getMap() {
		return map;
	}
}
