package gt.lastgnome.scoring;

import java.util.ArrayList;
import java.util.List;

public class NullHighscoreEntry extends HighscoreEntry {

	public NullHighscoreEntry() {
		super(0, 0, 0, 0);
		List<String> players = new ArrayList<String>();
		players.add("Nobody");
		setPlayers(players);
	}

}
