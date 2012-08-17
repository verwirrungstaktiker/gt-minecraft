package gt.lastgnome.scoring;

import java.util.List;

import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.exceptions.PersistanceException;

public class HighscoreEntry implements Comparable<HighscoreEntry>{
	
	private int time;
	private int damage;
	private int deaths;
	private int points;
	private List<String> players;
	
	private final static String KEY_TIME = "time";
	private final static String KEY_DAMAGE = "damage";
	private final static String KEY_DEATHS = "deaths";
	private final static String KEY_POINTS = "points";
	private final static String KEY_PLAYERS = "players";
	
	
	
	public HighscoreEntry() {
	}
	
	public HighscoreEntry(int time, int damage, int deaths,
			int points) {
		this.time = time;
		this.damage = damage;
		this.deaths = deaths;
		this.points = points;
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
	
	public void setPlayers(List<String> players) {
		this.players = players;		
	}
	
	public List<String> getPlayers() {
		return players;
	}
	
	public void setup(final PersistanceMap map) throws PersistanceException {
		this.time = map.get(KEY_TIME);
		this.damage = map.get(KEY_DAMAGE);
		this.deaths = map.get(KEY_DEATHS);
		this.points = map.get(KEY_POINTS);
		
		players = map.get(KEY_PLAYERS);
	}
	
	public PersistanceMap dump() {
		PersistanceMap map = new PersistanceMap();
		
		map.put(KEY_TIME, time);
		map.put(KEY_DAMAGE, damage);
		map.put(KEY_DEATHS, deaths);
		map.put(KEY_POINTS, points);
		
		map.put(KEY_PLAYERS, players);
		
		return map;
	}

	@Override
	public int compareTo(HighscoreEntry o) {
		return getPoints()-o.getPoints();
	}
	
	
}
