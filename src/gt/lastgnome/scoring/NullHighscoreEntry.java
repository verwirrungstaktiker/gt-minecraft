/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.lastgnome.scoring;

import static com.google.common.collect.Lists.newArrayList;

public class NullHighscoreEntry extends HighscoreEntry {

	/**
	 * Construct a new Dummy-HighscoreEntry
	 */
	public NullHighscoreEntry() {
		super(0, 0, 0, 0);
		setPlayers(newArrayList("----","----","----","----"));
	}

}
