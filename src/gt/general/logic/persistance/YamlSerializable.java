package gt.general.logic.persistance;

import gt.general.logic.persistance.exceptions.PersistanceException;
import gt.general.world.WorldInstance;

import java.util.Set;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;

public abstract class YamlSerializable {
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
	 * @throws PersistanceException 
	 */
	public abstract void setup(PersistanceMap values, World world) throws PersistanceException;
	
	/**
	 * load Metadata from a Yaml File
	 * @param file a Yaml File that is read
	 * @param worldInstance the WorldInstance that will get the loaded data
	 * @throws PersistanceException 
	 */
	public void setup(final String file, final WorldInstance worldInstance) throws PersistanceException {
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
