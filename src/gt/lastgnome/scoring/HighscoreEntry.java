package gt.lastgnome.scoring;

import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.exceptions.PersistanceException;

public class HighscoreEntry implements Comparable<HighscoreEntry>{
	
	private final String name;
	private int time;
	private int damage;
	private int deaths;
	private int points;
	
	private final String KEY_TIME = "time";
	private final String KEY_DAMAGE = "damage";
	private final String KEY_DEATHS = "deaths";
	private final String KEY_POINTS = "points";
	
	public HighscoreEntry(final String name) {
		this.name = name;
	}
	
	public HighscoreEntry(String name, int time, int damage, int deaths,
			int points) {
		this.name = name;
		this.time = time;
		this.damage = damage;
		this.deaths = deaths;
		this.points = points;
	}

	public String getName() {
		return name;
	}
	public int getTime() {
		return time;
	}
	public int getDamage() {
		return damage;
	}
	public int getDeaths() {
		return deaths;
	}
	public int getPoints() {
		return points;
	}
	
	public void setup(final PersistanceMap map) throws PersistanceException {
		this.time = map.get(KEY_TIME);
		this.damage = map.get(KEY_DAMAGE);
		this.deaths = map.get(KEY_DEATHS);
		this.points = map.get(KEY_POINTS);
	}
	
	public PersistanceMap dump() {
		PersistanceMap map = new PersistanceMap();
		
		map.put(KEY_TIME, time);
		map.put(KEY_DAMAGE, damage);
		map.put(KEY_DEATHS, deaths);
		map.put(KEY_POINTS, points);		

		return map;
	}

	@Override
	public int compareTo(HighscoreEntry o) {
		return getPoints()-o.getPoints();
	}
	
	
}
