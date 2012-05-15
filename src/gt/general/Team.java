package gt.general;

import org.apache.commons.lang.ArrayUtils;
/**
 * Team which manages a group of four players
 */
public class Team {
	/**The empty team, assigned to all teamless heros*/
	public static final Team NOTEAM = new Team(new Hero[]{});

	/**Array of heros belonging to this team*/
	private Hero[] members;

	/**
	 * @param heroes the initial members of this team
	 */
	public Team(final Hero[] heroes) {
		super();
		this.setPlayers(heroes);

		for (int i=0; i<heroes.length;++i) {
			heroes[i].setTeam(this);
		}
	}

	/**
	 * @return the players of this team
	 */
	public Hero[] getPlayers() {
		return members;
	}

	/**
	 * @param players the members of this team
	 */
	public void setPlayers(final Hero[] players) {
		this.members = players;
	}

	/**
	 * @param hero the hero to be checked
	 * @return true if hero is a member of this team
	 */
	public boolean isMember(final Hero hero) {
		return ArrayUtils.contains(members, hero);
	}
}
