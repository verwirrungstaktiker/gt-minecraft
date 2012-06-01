package gt.general;

import java.util.HashSet;
import java.util.Set;

/**
 * Team which manages a group of four players
 */
public class Team {
	/** The empty team, assigned to all teamless heros */
	public static final Team NOTEAM = new Team(new HashSet<Hero>());

	/** Array of heros belonging to this team */
	private Set<Hero> members;
	
	/** The Game this Team is currently playing */
	private Game game;

	/**
	 * @param members the initial members of this team
	 */
	public Team(final Set<Hero> members) {		
		this.members = members;

		for(Hero member : members) {
			member.setTeam(this);
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
}
