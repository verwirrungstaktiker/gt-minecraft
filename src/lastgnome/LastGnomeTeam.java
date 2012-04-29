package lastgnome;

import general.Team;

public class LastGnomeTeam extends Team {

	LastGnomePlayer[] players;
	LastGnomePlayer gnomeBearer; //so that e.g. Zombies know who the Gnome-Bearer is

	public LastGnomeTeam(LastGnomePlayer[] players, LastGnomePlayer gnomeBearer) {
		super(players);

		this.gnomeBearer = gnomeBearer;
	}
	
	public void setGnomeCarrier(LastGnomePlayer gnomeCarrier) {
		this.gnomeBearer = gnomeCarrier;
	}
	
	public LastGnomePlayer getGnomeCarrier() {
		return gnomeBearer;
	}

}
