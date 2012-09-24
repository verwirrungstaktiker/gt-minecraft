/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.lastgnome.scoring;

import gt.general.character.Hero;
import gt.general.character.Team;

import java.util.HashMap;
import java.util.Map;

public class Score {
	
	private int time;	// *1ms
	private int totalDamage;
	private int totalDeaths;
	private Map<Hero,Integer> deaths;
	private Map<Hero,Integer> damage;
	
	/**
	 * Construct a new Score
	 * @param team corresponding Team of Players
	 */
	public Score(final Team team) {
		time = 0;
		totalDamage = 0;
		totalDeaths = 0;
		
		deaths = new HashMap<Hero, Integer>();
		damage = new HashMap<Hero, Integer>();
		for (Hero hero: team.getPlayers()) {
			deaths.put(hero, 0);
			damage.put(hero, 0);
		}		
	}

	/**
	 * @return time needed
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param time time needed
	 */
	public void setTime(final int time) {
		this.time = time;
	}
	
	/**
	 * @param millies millies added to needed time
	 */
	public void addTime(final long millies) {
		time += millies;
	}

	/**
	 * @return total damage taken by the team
	 */
	public int getTotalDamage() {
		return totalDamage;
	}

	/**
	 * @return total deaths in the team
	 */
	public int getTotalDeaths() {
		return totalDeaths;
	}

	/**
	 * @param hero one hero of a team
	 * @return total deaths of the hero
	 */
	public int getDeaths(final Hero hero) {
		return deaths.get(hero);
	}

	/**
	 * add a death for a hero
	 * @param hero one hero of a team
	 */
	public void addDeath(final Hero hero) {
		int value = deaths.get(hero);
		value++;
		totalDeaths++;
		deaths.put(hero, value);
	}

	/**
	 * @param hero one hero of a team
	 * @return total damage taken from a hero
	 */
	public int getDamage(final Hero hero) {
		return damage.get(hero);
	}

	/**
	 * add damage taken for a hero
	 * @param hero one hero of a team
	 * @param dmg damage taken to be added
	 */
	public void addDamage(final Hero hero, final int dmg) {
		int value = damage.get(hero);
		value += dmg;
		totalDamage += dmg;
		damage.put(hero, value);
	}
	
	/**
	 * @return the calculated Highscore Points
	 */
	public int getPoints() {
		double averageTime = 20*60*1000; //Scoremanager counts millies 

		System.out.println("new score: \n" +
				"time: " + time + " \n" + 
				"totalDamage: " + totalDamage + " \n" +
				"totalDeaths: " + totalDeaths + " \n" +
				damage + " \n" +
				deaths );
		
		return (int) ((10.0/(totalDamage+10.0)+(1.0/(totalDeaths+1.0)))*averageTime/time*3000);
	}
	
	/**
	 * @return a HighscoreEnty Object for the current Scores
	 */
	public HighscoreEntry toHighscoreEntry() {
		return new HighscoreEntry(time, totalDamage, totalDeaths, getPoints());
	}
}
