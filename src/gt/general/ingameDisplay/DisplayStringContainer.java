/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation f�r kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ne� (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.ingameDisplay;

import java.util.Collection;

public interface DisplayStringContainer {

	/**
	 * @return all displayed strings
	 */
	Collection<DisplayString> getDisplayStrings();
	
}
