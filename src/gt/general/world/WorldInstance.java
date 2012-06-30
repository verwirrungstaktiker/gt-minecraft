package gt.general.world;

import gt.general.Spawn;
import gt.general.SpawnPersistance;
import gt.general.trigger.TriggerManager;
import gt.general.trigger.persistance.TriggerManagerPersistance;
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
	private File triggerFile;
	
	private Spawn spawn;
	private File spawnFile;
	
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

	public void loadTriggerManager() {
		try {
			Reader reader = Files.newReader(triggerFile, Charset.defaultCharset());
			new TriggerManagerPersistance(triggerManager, world).deserializeFrom(reader);
			
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void saveTriggerManager() {
		try {
			Writer writer = Files.newWriter(triggerFile, Charset.defaultCharset());
			new TriggerManagerPersistance(triggerManager, world).serializeTo(writer);
			
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void loadSpawn() {
		try {
			Reader reader = Files.newReader(spawnFile, Charset.defaultCharset());
			new SpawnPersistance(spawn, world).deserializeFrom(reader);
			
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void saveSpawn() {
		try {
			Writer writer = Files.newWriter(spawnFile, Charset.defaultCharset());
			new SpawnPersistance(spawn, world).serializeTo(writer);
			
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
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
			throw new RuntimeException(e);
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

	public void setSpawn(Spawn spawn) {
		this.spawn = spawn;
	}

	public void setTriggerManager(TriggerManager triggerManager) {
		this.triggerManager = triggerManager;
	}

}
