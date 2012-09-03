package gt.lastgnome.scoring;

import gt.general.character.Hero;
import gt.general.character.Team;

import java.util.HashMap;
import java.util.Map;

public class Score {
	
	private int time;	// *100ms
	private int totalDamage;
	private int totalDeaths;
	private Map<Hero,Integer> deaths;
	private Map<Hero,Integer> damage;
	
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

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	public void addTime(long millies) {
		time += millies;
	}

	public int getTotalDamage() {
		return totalDamage;
	}

	public int getTotalDeaths() {
		return totalDeaths;
	}

	public int getDeaths(Hero hero) {
		return deaths.get(hero);
	}

	public void addDeath(Hero hero) {
		int value = deaths.get(hero);
		value++;
		totalDeaths++;
		deaths.put(hero, value);
	}

	public int getDamage(Hero hero) {
		return damage.get(hero);
	}

	public void addDamage(Hero hero, int dmg) {
		int value = damage.get(hero);
		value += dmg;
		totalDamage += dmg;
		damage.put(hero, value);
	}
	
	public int getPoints() {
		//TODO: improve Forumla
		int averageTime = 20*60*10; //Scoremanager schedules 10times per Second 

		//TODO this is debugging output
		System.out.println("time: " + time + " \n" + 
				"totalDamage: " + totalDamage + " \n" +
				"totalDeaths: " + totalDeaths + " \n" +
				damage + " \n" +
				deaths );
		
		return (400-totalDamage-totalDeaths*2)*averageTime/time;
	}
	
	public HighscoreEntry toHighscoreEntry() {
		return new HighscoreEntry(time, totalDamage, totalDeaths, getPoints());
	}
}
