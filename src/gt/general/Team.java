package gt.general;

public class Team {

	public static final Team NOTEAM = new Team(new Hero[]{});
	
	private Hero[] players;

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
		return players;
	}

	/**
	 * @param players the members of this team
	 */
	public void setPlayers(final Hero[] players) {
		this.players = players;
	}

}
