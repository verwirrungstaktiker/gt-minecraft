package gt.general;

import java.io.Reader;
import java.io.Writer;
import java.util.Map;

import org.bukkit.World;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;

public class SpawnPersistance {
	
	private final Spawn spawn;
	private final World world;
	
	private final Yaml yaml;
	
	public static final String KEY_SPAWN = "spawn"; 

	public static final String KEY_CLASS = "class";
	
	
	/**
	 * @param triggerManager the TriggerManager to be persisted
	 * @param world the to which the TriggerManager is associated
	 */
	public SpawnPersistance(final Spawn spawn, final World world) {
		this.spawn = spawn;
		this.world = world;

		DumperOptions opts = new DumperOptions();
		opts.setDefaultFlowStyle(FlowStyle.BLOCK);
		opts.setPrettyFlow(true);
		
		yaml = new Yaml(opts);
	}

	/**
	 * @param writer where the TriggerManager should be dumped
	 */
	public void serializeTo(final Writer writer) {
		yaml.dump(asYaml(), writer);
	}

	/*
	 * TODO convert classcast into something checked
	 */
	public void deserializeFrom(final Reader reader) {
		try {
			loadSpawnFile(reader);
		} catch (ClassCastException e) {
			throw new RuntimeException("bad file format", e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	
	@SuppressWarnings("unchecked")
	private void loadSpawnFile(final Reader reader) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		Map<String, Object> values = (Map<String, Object>) yaml.load(reader);
		
		// if empty then empty
		if(values == null) {
			return;
		}
		
		spawn.setup(values, world);
	}

	
	/**
	 * @return yamlable representation of the contained TriggerManager
	 */
	public Map<String, Object> asYaml() {
		return spawn.dump();
	}
}
