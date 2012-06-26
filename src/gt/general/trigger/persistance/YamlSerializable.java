package gt.general.trigger.persistance;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.block.Block;

public abstract class YamlSerializable {

	public static final String KEY_X_COORDINATE = "x";
	public static final String KEY_Y_COORDINATE = "y";
	public static final String KEY_Z_COORDINATE = "z";
	
	private String label;
	
	/**
	 * Generates a new YamlSerializable. Uses the class name for the label.
	 */
	public YamlSerializable() {
		setLabel(this.getClass().getName() + "_"+ hashCode());
	}
	
	/**
	 * @param labelPrefix the prefix for the label.
	 */
	public YamlSerializable(final String labelPrefix) {
		setLabel(labelPrefix + "_" + hashCode());
	}
	
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the new Label
	 */
	public void setLabel(final String label) {
		this.label = label;
	}
	
	/**
	 * @param values mapping - e.g. received by the deserialization process
	 * @param world where the block can be found
	 * @return the Block
	 */
	public Block blockFromCoordinates(final Map<String, Object> values, final World world) {
		int x = (Integer) values.get(KEY_X_COORDINATE);
		int y = (Integer) values.get(KEY_Y_COORDINATE);
		int z = (Integer) values.get(KEY_Z_COORDINATE);
		
		return world.getBlockAt(x, y, z);
	}
	
	/**
	 * @param block the block to be serialized
	 * @return mapping - e.g. to be used in the serialization process
	 */
	public Map<String, Object> coordinatesFromBlock(final Block block) {
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
	
	/**
	 * @return a Set of all Blocks that correspond to the Serializable Object. Empty Set if no corresponding Blocks.
	 */
	public abstract Set<Block> getBlocks();
}
