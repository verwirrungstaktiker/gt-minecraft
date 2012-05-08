package gt.general;

public class Team {

	public static Team NOTEAM = new Team(new Hero[]{});
	
	Hero[] players;

	public Team(Hero[] players) {
		super();
		this.players = players;
		for (int i=0; i<players.length;++i) {
			players[i].setTeam(this);
		}
	}

}
