package gt.general;

public class Team {

	public static Team NOTEAM = new Team(new Hero[]{});
	
	Hero[] players;

	public Team(Hero[] heroes) {
		super();
		this.players = heroes;
		for (int i=0; i<heroes.length;++i) {
			heroes[i].setTeam(this);
		}
	}

}
