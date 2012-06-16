package gt.general.trigger.persistance;

import java.util.Map;

import org.bukkit.World;

public interface YamlSerializable {

	// TODO abstract class?
	String getLabel ();
	void setLabel(String label);
	
	/**
	 * 
	 * @param values map containing coordinates & orientation
	 * @param world the minecraft world
	 */
	void setup(Map<String, Object> values, World world);
	
	/**
	 * 
	 * @return the corresponding map containing coordinates & orientation
	 */
	Map<String, Object> dump();
	
	/**
	 * delete the corresponding block in the world
	 */
	void dispose();
}
