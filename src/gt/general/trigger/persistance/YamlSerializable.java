package gt.general.trigger.persistance;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.World;
import org.bukkit.block.Block;

public abstract class YamlSerializable {

	public final static String KEY_X_COORDINATE = "x";
	public final static String KEY_Y_COORDINATE = "y";
	public final static String KEY_Z_COORDINATE = "z";
	
	private String label;
	
	public YamlSerializable() {
		setLabel(this.getClass().getName() + "_"+ hashCode());
	}
	
	public YamlSerializable(final String labelPrefix) {
		setLabel(labelPrefix + "_" + hashCode());
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(final String label) {
		this.label = label;
	}
	
	protected Block blockFromCoordinates(final Map<String, Object> values, final World world) {
		int x = (Integer) values.get(KEY_X_COORDINATE);
		int y = (Integer) values.get(KEY_Y_COORDINATE);
		int z = (Integer) values.get(KEY_Z_COORDINATE);
		
		return world.getBlockAt(x, y, z);
	}
	
	protected Map<String, Object> coordinatesFromPoint(final Block block) {
		Map<String, Object> map = new HashMap<String,Object>();

		map.put(KEY_X_COORDINATE, block.getX());
		map.put(KEY_Y_COORDINATE, block.getY());
		map.put(KEY_Z_COORDINATE, block.getZ());
		
		return map;
	}
	
	/**
	 * 
	 * @param values map containing coordinates & orientation
	 * @param world the minecraft world
	 */
	public abstract void setup(Map<String, Object> values, World world);
	
	/**
	 * 
	 * @return the corresponding map containing coordinates & orientation
	 */
	public abstract Map<String, Object> dump();
	
	/**
	 * delete the corresponding block in the world
	 */
	public abstract void dispose();
}
