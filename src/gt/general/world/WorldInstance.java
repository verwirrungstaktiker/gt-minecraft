package gt.general.world;

import gt.general.Spawn;
import gt.general.trigger.TriggerManager;
import gt.general.trigger.persistance.PersistanceMap;
import gt.general.trigger.persistance.YamlSerializable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.yaml.snakeyaml.Yaml;

import com.google.common.io.Files;

/**
 * Represents one Instance of a World including metadata.
 * 
 * @author Sebastian Fahnenschreiber
 *
 */
public class WorldInstance {
	
	private World world;
	private String name;
	
	private TriggerManager triggerManager;	
	private Spawn spawn;	
	
	
	/**
	 * @param world the minecraft representation of this world
	 */
	public WorldInstance(final World world) {
		this.world = world;
	}
	
	/**
	 * @return the minecraft representation of this world
	 */
	public World getWorld() {
		return world;
	}
	
	/**
	 * @param name the name of this instance
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the name of this instance
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * intializes this world instance
	 *  
	 * @param triggerManager the Triggermanager to be used
	 */
	public void init(final TriggerManager triggerManager) {
		triggerManager.setup(TriggerManager.PERSISTANCE_FILE, this);
		this.triggerManager = triggerManager;
		
		Spawn spawn = new Spawn();
		spawn.setup(Spawn.PERSISTANCE_FILE, this);
		this.spawn = spawn;
	}
	
	/**
	 * @return the triggerManager
	 */
	public TriggerManager getTriggerManager() {
		return triggerManager;
	}

	/**
	 * @return the Spawn associated with this Worldinstance
	 */
	public Spawn getSpawn() {
		return spawn;
	}

	/**
	 * saves this worldInstance
	 */
	public void save() {
		saveMeta(TriggerManager.PERSISTANCE_FILE, triggerManager);
		saveMeta(Spawn.PERSISTANCE_FILE, spawn);
		
		world.save();
	}
	
	/**
	 * disposes this worldInstance
	 */
	public void dispose() {
		triggerManager.dispose();
		spawn.dispose();
	}
	
	/**
	 * reads a yaml file into a map
	 * 
	 * @param fileName the file to load
	 * @return a map representing the file contents
	 */
	public PersistanceMap loadMeta(final String fileName) {		
		try {
			File path = new File(world.getWorldFolder(), fileName);
			Reader reader = Files.newReader(path, Charset.defaultCharset());
			
			Yaml yaml = new Yaml(YamlSerializable.YAML_OPTIONS);
			
			@SuppressWarnings("unchecked")
			Map<String, Object> values = (Map<String, Object>) yaml.load(reader);
			
			return new PersistanceMap(values);
		
		} catch (ClassCastException e) {
			throw new RuntimeException("cannot cast contents of " + fileName, e);
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	/**
	 * writes a map into a yaml file
	 * 
	 * @param fileName the file to write
	 * @param values the data to write
	 */
	public void saveMeta(final String fileName, final PersistanceMap values) {
		try {
			File path = new File(world.getWorldFolder(), fileName);
			Writer writer = Files.newWriter(path, Charset.defaultCharset());
			
			Yaml yaml = new Yaml(YamlSerializable.YAML_OPTIONS);
			yaml.dump(values, writer);
			
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * writes a YamlSerializable to a yaml file
	 * 
	 * @param fileName the file to write
	 * @param serializable the object to write
	 */
	public void saveMeta(final String fileName, final YamlSerializable serializable) {
		saveMeta(fileName, serializable.dump());
	}

	/**
	 * shortcut
	 * 
	 * @return where the players spawn
	 */
	public Location getSpawnLocation() {
		return world.getSpawnLocation();
	}
}
