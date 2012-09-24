/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic.persistence;

import gt.general.logic.persistence.exceptions.PersistenceException;
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
	 * @throws PersistenceException 
	 */
	public abstract void setup(PersistenceMap values, World world) throws PersistenceException;
	
	/**
	 * load Metadata from a Yaml File
	 * @param file a Yaml File that is read
	 * @param worldInstance the WorldInstance that will get the loaded data
	 * @throws PersistenceException 
	 */
	public void setup(final String file, final WorldInstance worldInstance) throws PersistenceException {
		setup(worldInstance.loadMeta(file), worldInstance.getWorld());
	}
	
	/**
	 * 
	 * @return the corresponding map containing coordinates & orientation
	 */
	public abstract PersistenceMap dump();
	
	/**
	 * delete the corresponding block in the world
	 */
	public abstract void dispose();
	
	/**
	 * @return a Set of all Blocks that correspond to the Serializable Object. Empty Set if no corresponding Blocks.
	 */
	public abstract Set<Block> getBlocks();
}
