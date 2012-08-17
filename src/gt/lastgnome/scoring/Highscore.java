package gt.lastgnome.scoring;

import java.util.ArrayList;
import java.util.List;

public class Highscore {

	private final List<HighscoreEntry> scores;
	
	public Highscore() {
		scores = new ArrayList<HighscoreEntry>();
	}
	
	public List<HighscoreEntry> getScores() {
		return scores;
	}
	
}
