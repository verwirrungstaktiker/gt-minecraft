package gt.general.character;

import gt.general.Game;

import java.util.HashSet;
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

		observers = new HashSet<TeamObserver>();
		
		for(Hero member : members) {
			member.setTeam(this);
		}
	}
	
	public Team(final Hero initiator) {
		this();
		
		members.add(initiator);
		
		// abstract this		
		for(Hero member : members) {
			member.setTeam(this);
		}
	}
	
	public Team() {
		members = new HashSet<Hero>();
		observers = new HashSet<TeamObserver>();
	}
	
	public void dispose() {
		
		for(Hero member : members) {
			member.setTeam(NOTEAM);
		}
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
	 * @param location Where to teleport the whole Team
	 */
	public void teleportTo(final Location location) {
		for (Hero hero : getPlayers()) {
			hero.getPlayer().teleport(location);
		}
	}

	public void addTeamObserver(final TeamObserver teamObserver) {
		observers.add(teamObserver);
	}
	
	public void removeTeamObserver(final TeamObserver teamObserver) {
		observers.remove(teamObserver);
	}
	
	public void notifyChanged(final Notification notification) {
		for(TeamObserver observer : observers) {
			observer.update(this, notification);
		}
	}

	public void addHero(Hero hero) {
		members.add(hero);
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

	public void removeHero(Hero member) {
		members.remove(member);
		notifyChanged(Notification.MEMBERCHANGE);
	}
	
}
