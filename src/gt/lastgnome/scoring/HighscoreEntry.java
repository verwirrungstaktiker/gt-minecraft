package gt.lastgnome.scoring;

import static com.google.common.collect.Lists.*;

import java.util.List;

import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.exceptions.PersistenceException;

public class HighscoreEntry implements Comparable<HighscoreEntry>{
	
	private int time = 0;
	private int damage = 0;
	private int deaths = 0;
	private int points = 0;
	private List<String> players = newArrayList();
	
	private final static String KEY_TIME = "time";
	private final static String KEY_DAMAGE = "damage";
	private final static String KEY_DEATHS = "deaths";
	private final static String KEY_POINTS = "points";
	private final static String KEY_PLAYERS = "players";
	
	
	
	public HighscoreEntry() {}
	
	public HighscoreEntry(int time, int damage, int deaths,
			int points) {
		this.time = time;
		this.damage = damage;
		this.deaths = deaths;
		this.points = points;
	}
	
	public void setup(final PersistenceMap map) throws PersistenceException {
		this.time = map.get(KEY_TIME);
		this.damage = map.get(KEY_DAMAGE);
		this.deaths = map.get(KEY_DEATHS);
		this.points = map.get(KEY_POINTS);
		
		players = map.get(KEY_PLAYERS);
	}
	
	public PersistenceMap dump() {
		PersistenceMap map = new PersistenceMap();
		
		map.put(KEY_TIME, time);
		map.put(KEY_DAMAGE, damage);
		map.put(KEY_DEATHS, deaths);
		map.put(KEY_POINTS, points);
		
		map.put(KEY_PLAYERS, players);
		
		return map;
	}

	@Override
	public int compareTo(HighscoreEntry o) {
		return -Integer.valueOf(getPoints()).compareTo(o.getPoints());
	}

	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * @return the damage
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * @param damage the damage to set
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	/**
	 * @return the deaths
	 */
	public int getDeaths() {
		return deaths;
	}

	/**
	 * @param deaths the deaths to set
	 */
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * @return the players
	 */
	public List<String> getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(List<String> players) {
		this.players = players;
	}
	
	public void addPlayer(final String name) {
		players.add(name);
	}
	
}
