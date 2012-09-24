/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.lastgnome.scoring;

import static com.google.common.collect.Lists.newArrayList;
import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.exceptions.PersistenceException;

import java.util.List;

public class HighscoreEntry implements Comparable<HighscoreEntry>{
	
	private int time = 0;
	private int damage = 0;
	private int deaths = 0;
	private int points = 0;
	private List<String> players = newArrayList();
	
	private static final String KEY_TIME = "time";
	private static final String KEY_DAMAGE = "damage";
	private static final String KEY_DEATHS = "deaths";
	private static final String KEY_POINTS = "points";
	private static final String KEY_PLAYERS = "players";
	
	/** construct new Highscore Entry */
	public HighscoreEntry() {}
	
	/**
	 * @param time time needed
	 * @param damage damage taken by all players
	 * @param deaths deaths by all players
	 * @param points points that have been scored
	 */
	public HighscoreEntry(final int time, final int damage, final int deaths,
			final int points) {
		this.time = time;
		this.damage = damage;
		this.deaths = deaths;
		this.points = points;
	}
	
	/**
	 * de-serialize scoring data
	 * @param map source of data
	 * @throws PersistenceException error in the persistence of serialized data
	 */
	public void setup(final PersistenceMap map) throws PersistenceException {
		this.time = map.get(KEY_TIME);
		this.damage = map.get(KEY_DAMAGE);
		this.deaths = map.get(KEY_DEATHS);
		this.points = map.get(KEY_POINTS);
		
		players = map.get(KEY_PLAYERS);
	}
	
	/**
	 * serialize scoring data
	 * @return serialized data
	 */
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
	public int compareTo(final HighscoreEntry o) {
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
	public void setTime(final int time) {
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
	public void setDamage(final int damage) {
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
	public void setDeaths(final int deaths) {
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
	public void setPoints(final int points) {
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
	public void setPlayers(final List<String> players) {
		this.players = players;
	}
	
	/**
	 * @param name name of the player to be added
	 */
	public void addPlayer(final String name) {
		players.add(name);
	}
	
}
