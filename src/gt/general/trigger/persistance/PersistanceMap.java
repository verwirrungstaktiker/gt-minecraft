package gt.general.trigger.persistance;

import static com.google.common.collect.Maps.*;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.Block;


public class PersistanceMap {

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
	
	public String getString(final String key) {
		return (String) map.get(key);
	}
	public Block getBlock(final String key) {
		return null;
	}
	
	public Location getLocation(final String key) {
		return null;
	}
	
	
	public void put(final String key, final Object value) {
		map.put(key, value);
	}

	public void put(final String key, final Block value) {
	}
	
	public void put(final String key, final Location value) {
	}
	
}
