package gt.lastgnome.scoring;

import static com.google.common.collect.Lists.*;

import java.util.ArrayList;
import java.util.List;

public class NullHighscoreEntry extends HighscoreEntry {

	public NullHighscoreEntry() {
		super(0, 0, 0, 0);
		setPlayers(newArrayList("----","----","----","----"));
	}

}
