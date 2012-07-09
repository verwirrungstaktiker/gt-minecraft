package gt.general.trigger.persistance;

import gt.general.world.WorldInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;

public abstract class YamlSerializable {

	public static final String KEY_X_COORDINATE = "x";
	public static final String KEY_Y_COORDINATE = "y";
	public static final String KEY_Z_COORDINATE = "z";
	
	private String label;
	
	public static final DumperOptions YAML_OPTIONS;
	
	static {
		YAML_OPTIONS = new DumperOptions();
		YAML_OPTIONS.setDefaultFlowStyle(FlowStyle.BLOCK);
		YAML_OPTIONS.setPrettyFlow(true);
	}
	
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
	 * 
	 * @param values map containing coordinates & orientation
	 * @param world the minecraft world
	 */
	public abstract void setup(PersistanceMap values, World world);
	
	
	public void setup(final String file, final WorldInstance worldInstance) {
		setup(worldInstance.loadMeta(file), worldInstance.getWorld());
	}
	
	/**
	 * 
	 * @return the corresponding map containing coordinates & orientation
	 */
	public abstract PersistanceMap dump();
	
	/**
	 * delete the corresponding block in the world
	 */
	public abstract void dispose();
	
	/**
	 * @return a Set of all Blocks that correspond to the Serializable Object. Empty Set if no corresponding Blocks.
	 */
	public abstract Set<Block> getBlocks();
}
