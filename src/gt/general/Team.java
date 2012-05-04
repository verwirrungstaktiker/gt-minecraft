package gt.general;

public class Team {

	public static Team NOTEAM = new Team(new Player[]{});
	
	Player[] players;

	public Team(Player[] players) {
		super();
		this.players = players;
	}

}
