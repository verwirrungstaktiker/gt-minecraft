package gt.lastgnome;

import gt.general.Team;

public class LastGnomeTeam extends Team {

	LastGnomeHero[] players;
	LastGnomeHero gnomeBearer; //so that e.g. Zombies know who the Gnome-Bearer is

	public LastGnomeTeam(LastGnomeHero[] players, LastGnomeHero gnomeBearer) {
		super(players);
		for (int i=0; i<players.length;++i) {
			players[i].setTeam(this);
		}
		this.gnomeBearer = gnomeBearer;
	}
	
	public void setGnomeBearer(LastGnomeHero gnomeBearer) {
		this.gnomeBearer = gnomeBearer;
	}
	
	public LastGnomeHero getGnomeBearer() {
		return gnomeBearer;
	}

}
