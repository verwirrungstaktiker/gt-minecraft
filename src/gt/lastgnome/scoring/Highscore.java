package gt.lastgnome.scoring;

import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.YamlSerializable;
import gt.general.logic.persistance.exceptions.PersistanceException;
import gt.plugin.meta.Hello;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


import org.yaml.snakeyaml.Yaml;

import com.google.common.io.Files;

public class Highscore {

	private final List<HighscoreEntry> scores;
	
	private final static String PERSISTANCE_FILE="highscore.yml";
	private final static String KEY_SCORES="scores";
	
	/**
	 * Creates a new Highscore
	 */
	public Highscore() {
		scores = new ArrayList<HighscoreEntry>();
	}
	
	/**
	 * Creates a new Highscore and loads entrys for the specified world
	 * @param worldName the specified world
	 */
	public Highscore(String worldName) {
		scores = new ArrayList<HighscoreEntry>();
		loadScores(worldName);
	}
	
	public List<HighscoreEntry> getScores() {
		return scores;
	}
	
	/**
	 * Loads the highscore of the specified world
	 * @param worldName specified world
	 */
	@SuppressWarnings("unchecked")
	public void loadScores(String worldName) {
		File file = Hello.getPlugin().getServer().getWorldContainer();
		File worldFolder = new File(file, worldName);
		File path = new File(worldFolder, PERSISTANCE_FILE);
		try {
			Reader reader = Files.newReader(path, Charset.defaultCharset());
		
		
		Yaml yaml = new Yaml(YamlSerializable.YAML_OPTIONS);	
		
		Map<String, Object> values = (Map<String, Object>) yaml.load(reader);
		//PersistanceMap map = new PersistanceMap(values);
		
		List<Map<String, Object>> entries = (List<Map<String, Object>>) values.get(KEY_SCORES);
		for (Map<String, Object> scoreMap : entries) {
			HighscoreEntry entry = new HighscoreEntry();
			try {
				entry.setup(new PersistanceMap(scoreMap));
			} catch (PersistanceException e) {
				
			}
			scores.add(entry);
		}
		Collections.sort(scores);
		//PersistanceMap map = new PersistanceMap(values);
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds a new Highscore-Entry to the highscore
	 * @param entry the entry to be added
	 */
	public void addEntry(HighscoreEntry entry) {
		scores.add(entry);
		Collections.sort(scores);		
	}
	
	/**
	 * Saves the Highscore to the specified world
	 * @param worldName
	 */
	public void saveScores(String worldName) {
		PersistanceMap values = new PersistanceMap();
		List<Map<String,Object>> entries = new ArrayList<Map<String,Object>>();
		for (HighscoreEntry entry : scores) {
			entries.add(entry.dump().getMap());
		}
		
		values.put(KEY_SCORES, entries);
		
		try {
			File file = Hello.getPlugin().getServer().getWorldContainer();
			File worldFolder = new File(file, worldName);
			File path = new File(worldFolder, PERSISTANCE_FILE);
			Writer writer = Files.newWriter(path, Charset.defaultCharset());
			
			Yaml yaml = new Yaml(YamlSerializable.YAML_OPTIONS);
			yaml.dump(values.getMap(), writer);
			
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
