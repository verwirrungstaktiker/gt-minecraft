package gt.general.world;

import gt.general.Spawn;
import gt.general.trigger.TriggerManager;
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
		
		/*
		// TODO refactor this -> init should not be done in this class -> controller
		triggerFile = new File(world.getWorldFolder(), "trigger.yml");
		this.triggerManager = triggerManager;
		loadTriggerManager();
		
		spawnFile = new File(world.getWorldFolder(), "spawn.yml");
		this.spawn = spawn;
		
		loadSpawn();
		*/
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
	 * shortcut
	 * 
	 * @return where the players spawn
	 */
	public Location getSpawnLocation() {
		return world.getSpawnLocation();
	};
	
	
	/**
	 * @return the triggerManager
	 */
	public TriggerManager getTriggerManager() {
		return triggerManager;
	}

	public Spawn getSpawn() {
		return spawn;
	}
	
	// TODO abstract file names
	public void init(final TriggerManager triggerManager) {
		triggerManager.setup("trigger.yml", this);
		this.triggerManager = triggerManager;
		
		Spawn spawn = new Spawn();
		spawn.setup("spawn.yml", this);
		this.spawn = spawn;
	}
	
	public void save() {
		saveMeta("trigger.yml", triggerManager);
		saveMeta("spawn.yml", spawn);
	}
	
	public void dispose() {
		triggerManager.dispose();
		spawn.dispose();
	}
	

	public Map<String, Object> loadMeta(final String fileName) {		
		try {
			File path = new File(world.getWorldFolder(), fileName);
			Reader reader = Files.newReader(path, Charset.defaultCharset());
			
			Yaml yaml = new Yaml(YamlSerializable.YAML_OPTIONS);
			
			@SuppressWarnings("unchecked")
			Map<String, Object> values = (Map<String, Object>) yaml.load(reader);
			
			return values;
		
		} catch (ClassCastException e) {
			throw new RuntimeException("cannot cast contents of " + fileName, e);
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	public void saveMeta(final String fileName, final Map<String, Object> values) {
		try {
			File path = new File(world.getWorldFolder(), fileName);
			Writer writer = Files.newWriter(path, Charset.defaultCharset());
			
			Yaml yaml = new Yaml(YamlSerializable.YAML_OPTIONS);
			yaml.dump(values, writer);
			
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void saveMeta(final String fileName, final YamlSerializable serializable) {
		saveMeta(fileName, serializable.dump());
	}
}
