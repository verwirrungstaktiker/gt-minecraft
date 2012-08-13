package gt.general.character;

import gt.general.Game;
import gt.general.aura.Effect;
import gt.general.aura.FreezeEffect;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.Location;

/**
 * Team which manages a group of four players
 */
public class Team {
	/**
	 * The empty team, assigned to all teamless heros
	 * TODO is this really that intelligent?
	 * 
	 */
	public static final Team NOTEAM = null;
	
	private boolean fixed;

	/** 
	 * Array of heros belonging to this team
	 * TODO this needs to be ordered
	 */
	private Set<Hero> members;
	
	/** The Game this Team is currently playing */
	private Game game;

	private final  Set<TeamObserver> observers;

	public enum Notification {
		// TODO fire this notification
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
	
	public void freezeAllHeros() {
		for(Hero h : members) {
			h.freeze();
		}
	}
	
	public void unfreezeAllHeros() {
		for(Hero h : members) {
			h.unfreeze();
		}
	}

}
