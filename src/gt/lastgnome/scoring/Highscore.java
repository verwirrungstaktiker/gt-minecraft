package gt.lastgnome.scoring;

import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.YamlSerializable;
import gt.plugin.meta.Hello;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.World;
import org.yaml.snakeyaml.Yaml;

import com.google.common.io.Files;

public class Highscore {

	private final List<HighscoreEntry> scores;
	
	private final static String PERSISTANCE_FILE="highscore.yml";
	
	public Highscore() {
		scores = new ArrayList<HighscoreEntry>();
	}
	
	public List<HighscoreEntry> getScores() {
		return scores;
	}
	
	public void loadScores(String worldName) {
		World world = Hello.getPlugin().getServer().getWorld(worldName);
		File path = new File(world.getWorldFolder(), PERSISTANCE_FILE);
		try {
			Reader reader = Files.newReader(path, Charset.defaultCharset());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Yaml yaml = new Yaml(YamlSerializable.YAML_OPTIONS);	
		
		Map<String, Object> values = (Map<String, Object>) yaml.load(reader);
		
		PersistanceMap map = new PersistanceMap(values);
		
		
	}
	
	public void addEntry(HighscoreEntry entry) {
		
	}
	
}
