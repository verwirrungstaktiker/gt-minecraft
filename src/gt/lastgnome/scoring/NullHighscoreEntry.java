package gt.lastgnome.scoring;

import static com.google.common.collect.Lists.*;

public class NullHighscoreEntry extends HighscoreEntry {

	public NullHighscoreEntry() {
		super(0, 0, 0, 0);
		setPlayers(newArrayList("----","----","----","----"));
	}

}
