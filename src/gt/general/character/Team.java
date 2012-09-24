/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.character;

import gt.general.Game;
import gt.general.aura.FreezeEffect.FreezeCause;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;

/**
 * Team which manages a group of four players
 */
public class Team {
	
	/** The empty team, assigned to all teamless heros */
	public static final Team NOTEAM = null;
	
	private boolean fixed;

	/** 
	 * Array of heros belonging to this team
	 */
	private Set<Hero> members;
	
	/** The Game this Team is currently playing */
	private Game game;

	private final  Set<TeamObserver> observers;

	public enum Notification {
		MEMBERCHANGE
	}
	
	/**
	 * @param members the initial members of this team
	 */
	public Team(final Set<Hero> members) {		
		this.members = members;
		fixed = false;

		observers = new HashSet<TeamObserver>();
		
		for(Hero member : members) {
			member.setTeam(this);
		}
		
		notifyChanged(Notification.MEMBERCHANGE);
	}
	
	/**
	 * Generates an empty Team.
	 */
	public Team() {
		members = new HashSet<Hero>();
		observers = new HashSet<TeamObserver>();
		fixed = false;
	}
	
	/**
	 * Disposes the Team.
	 */
	public void dispose() {
		
		for(Hero member : members) {
			member.setTeam(NOTEAM);
		}
		members.clear();
		
		notifyChanged(Notification.MEMBERCHANGE);
	}
	
	/**
	 * @return the players of this team
	 */
	public Set<Hero> getPlayers() {
		return members;
	}

	/**
	 * @param hero the hero to be checked
	 * @return true if hero is a member of this team
	 */
	public boolean isMember(final Hero hero) {
		return members.contains(hero);
	}
	
	/**
	 * @return the currently played Game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * @param game the Game to be played
	 */
	public void setGame(final Game game) {
		this.game = game;
	}
	
	/**
	 * @return true if the team is in a game
	 */
	public boolean inGame() {
		return game != null;
	}
	
	/**
	 * @param location Where to teleport the whole Team
	 */
	public void teleportTo(final Location location) {
		for (Hero hero : getPlayers()) {
			hero.getPlayer().teleport(location);
		}
	}

	/**
	 * @param teamObserver The TeamObserver to be added.
	 */
	public void addTeamObserver(final TeamObserver teamObserver) {
		observers.add(teamObserver);
	}
	
	/**
	 * @param teamObserver The TeamObserver to be removed.
	 */
	public void removeTeamObserver(final TeamObserver teamObserver) {
		observers.remove(teamObserver);
	}
	
	/**
	 * @param notification The type of change which happened.
	 */
	public void notifyChanged(final Notification notification) {
		for(TeamObserver observer : observers) {
			observer.update(this, notification);
		}
	}

	/**
	 * @param hero The Hero to be added to the Team.
	 */
	public void addHero(final Hero hero) {
		members.add(hero);
		notifyChanged(Notification.MEMBERCHANGE);
	}

	/**
	 * @param hero The Hero to be removed from the Team.
	 */
	public void removeHero(final Hero hero) {
		members.remove(hero);
		notifyChanged(Notification.MEMBERCHANGE);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("The team: \n");
		
		if(members.size() == 0) {
			sb.append("  no members! \n");
		} else {		
			for(Hero member : members) {
				sb.append("- " + member.getPlayer().getName() + "\n");
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * @return the fixed
	 */
	public boolean isFixed() {
		return fixed;
	}
	
	/**
	 * after calling this function, the team cannot be canged anymore
	 */
	public void fix() {
		fixed = true;
	}
	
	/**
	 * freeze movement for all heroes of a team
	 */
	public void freezeAllHeros() {
		for(Hero h : members) {
			h.pause();
		}
	}
	
	/**
	 * unfreeze movement for all heroes of a team
	 */
	public void unfreezeAllHeros() {
		for(Hero h : members) {
			h.resume(FreezeCause.PAUSE);
		}
	}

	/**
	 * @param hero a player
	 * @return true if hero is part of this team
	 */
	public boolean containsHero(final Hero hero) {
		return members.contains(hero);
	}

	/**
	 * send a chat message to the team
	 * @param string the message
	 */
	public void sendMessage(final String string) {
		for(Hero hero : members) {
			hero.getPlayer().sendMessage(string);
		}
		
	}
	
}
