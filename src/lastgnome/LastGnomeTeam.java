package lastgnome;

import general.Team;

public class LastGnomeTeam extends Team {

	LastGnomePlayer[] players;
	LastGnomePlayer gnomeBearer; //so that e.g. Zombies know who the Gnome-Bearer is

	public LastGnomeTeam(LastGnomePlayer[] players, LastGnomePlayer gnomeBearer) {
		super(players);

		this.gnomeBearer = gnomeBearer;
	}
	
	public void setGnomeBearer(LastGnomePlayer gnomeBearer) {
		this.gnomeBearer = gnomeBearer;
	}
	
	public LastGnomePlayer getGnomeBearer() {
		return gnomeBearer;
	}

}
