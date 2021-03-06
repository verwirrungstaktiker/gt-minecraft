/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic.persistence;

import static com.google.common.collect.Maps.newHashMap;

import gt.general.logic.persistence.exceptions.PersistenceException;
import gt.general.logic.persistence.exceptions.PersistenceTypeException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;


public class PersistenceMap {

	public static final String KEY_X_COORDINATE = "x";
	public static final String KEY_Y_COORDINATE = "y";
	public static final String KEY_Z_COORDINATE = "z";
	public static final String [] KEY_COORDINATES = {"x","y","z"};
	
	private Map<String, Object> map;
	private String lastKey;

	
	/**
	 * creates a new PersistenceMap
	 */
	public PersistenceMap() {
		map = newHashMap();
	}
	
	/**
	 * creates a new PersistenceMap
	 * @param map usual Map that shall be integrated
	 */
	public PersistenceMap(final Map<String, Object> map) {
		this.map = map;
	}
	
	/**
	 * @param key name of the field
	 * @return value corresponding to the key
	 */
	public int getInt(final String key) {
		Integer result = (Integer)map.get(key);
		return result.intValue();
	}
	
	/**
	 * @param key name of the field
	 * @return value corresponding to the key
	 */
	public double getDouble(final String key) {
		Double result = (Double)map.get(key);

		return result.doubleValue();
	}
	
	/**
	 * @param key name of the field
	 * @return value corresponding to the key
	 */
	public boolean getBoolean(final String key) {
		Boolean result = (Boolean)map.get(key);

		return result.booleanValue();
	}
	
	/**
	 * can be used for unknown type
	 * @param key name of the field
	 * @param <T> type of the value that shall be returned
	 * @return value corresponding to the key
	 * @throws PersistenceException 
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(final String key) throws PersistenceException {
		lastKey = key;
		
		T result = (T) map.get(key);
	
		if (result == null) {
			throw new PersistenceException(key);
		}
		
		return result;
	}
	
	/**
	 * gets a bukkit block
	 * @param key name of the field
	 * @param world world that holds the block
	 * @return bukkit block to the key
	 * @throws PersistenceException 
	 */
	public Block getBlock(final String key, final World world) throws PersistenceException {
		Map<String, Object> coords = get(key);
		return blockFromCoords(coords, world);
	}
	
	/**
	 * get a collection of bukkit blocks
	 * @param key name of the field
	 * @param world world that holds the block
	 * @return all blocks that correspond to the key
	 * @throws PersistenceException 
	 */
	@SuppressWarnings("unchecked")
	public Collection<Block> getBlocks(final String key, final World world) throws PersistenceException {
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
	 * @throws PersistenceException 
	 */
	private Block blockFromCoords( final Map<String, Object> coords, final World world) throws PersistenceException {
		Integer x[] = {0,0,0};
		
		for (int i=0; i<3;i++) {
			try {
				x[i] = (Integer) coords.get(KEY_COORDINATES[i]);
			} catch (ClassCastException e) {
				throw new PersistenceTypeException(KEY_COORDINATES[i]);
			}
			if (x[i] == null) {
				throw new PersistenceException(KEY_COORDINATES[i]);
			}
		}
				
		return world.getBlockAt(x[0],x[1],x[2]);
	}
	
	/**
	 * read a location 
	 * @param key name of the field
	 * @param world world that holds the location
	 * @return location that corresponds to a key
	 * @throws PersistenceException 
	 */
	public Location getLocation(final String key, final World world) throws PersistenceException {
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
		map.put(key, value);
	}

	/**
	 * save a block
	 * @param key name of the corresponding field
	 * @param block the Block that is saved
	 */
	public void put(final String key, final Block block) {	
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
	
	/**
	 * @return the last used key in the Map
	 */
	public String getLastKey() {
		return lastKey;
	}
}
