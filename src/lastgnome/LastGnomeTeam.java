package lastgnome;

import general.Team;

public class LastGnomeTeam extends Team {

	LastGnomePlayer[] players;
	LastGnomePlayer gnomeCarrier; //so that e.g. Zombies know who the Gnome-Carrier is

	public LastGnomeTeam(LastGnomePlayer[] players, LastGnomePlayer gnomeCarrier) {
		super(players);

		this.gnomeCarrier = gnomeCarrier;
	}
	
	public void setGnomeCarrier(LastGnomePlayer gnomeCarrier) {
		this.gnomeCarrier = gnomeCarrier;
	}
	
	public LastGnomePlayer getGnomeCarrier() {
		return gnomeCarrier;
	}

}
