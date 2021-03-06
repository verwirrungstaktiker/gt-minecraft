/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.ingameDisplay;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import gt.lastgnome.scoring.Highscore;
import gt.lastgnome.scoring.HighscoreEntry;
import gt.lastgnome.scoring.NullHighscoreEntry;

import java.util.Collection;
import java.util.List;

import org.bukkit.Location;

public class ScoreBoard implements DisplayStringContainer {

	private final List<HighscoreEntry> displayedEntries = newArrayList();
	private final List<Location> anchors;
	
	private final Highscore highscore;
	
	private final Collection<DisplayString> displayStrings = newHashSet();
	
	/**
	 * 
	 * @param highscore the Top 3 Scores
	 * @param anchors the edges of the Scoreboard where the DisplayStrings are spawned
	 */
	public ScoreBoard(final Highscore highscore, final List<Location> anchors) {
		this.highscore = highscore;
		
		this.anchors = anchors;
		
		displayedEntries.add(new NullHighscoreEntry());
		displayedEntries.add(new NullHighscoreEntry());
		displayedEntries.add(new NullHighscoreEntry());
		generateDisplayStrings();
	}
	
	@Override
	public Collection<DisplayString> getDisplayStrings() {
		
		List<HighscoreEntry> currentEntries = highscore.getScores();
		
		boolean tainted = false;
		
		for(int i = 0; i < Math.min(3, currentEntries.size()); i++) {
			
			if(displayedEntries.get(i) != currentEntries.get(i)) {
				displayedEntries.set(i, currentEntries.get(i));
				tainted = true;
			}
		}
		
		
		if(tainted) {
			generateDisplayStrings();
		}
		
		return displayStrings;
	}

	/**
	 * generate the DisplayStrings for a text 
	 * note: each Letter has its own DisplayString
	 */
	private void generateDisplayStrings() {
		displayStrings.clear();
		
		for(int i = 0; i < Math.min(anchors.size(), displayedEntries.size()); i++) {
			
			
			HighscoreEntry entry = displayedEntries.get(i);
			
			int offset = 0;
			for(String name : entry.getPlayers()) {
			
				Location pos = anchors.get(i).clone();
				pos.subtract(0.0, (offset++ + 0.05), 0.0);
				
				displayStrings.add(new DisplayString(name, 0.66f, pos));
			}
			
			
			Location rightPos = anchors.get(i).clone();
			rightPos.add(rightPos.getDirection().multiply(18)).subtract(0.0, 1.0, 0.0);
			
			displayStrings.add(new RightBoundedDisplayString(String.valueOf(entry.getPoints()), 2f, rightPos));
			
		}
	}
}
