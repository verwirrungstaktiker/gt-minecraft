/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.aura;

import gt.general.character.Character;

/**
 * Effect, that can affect characters
 */
public interface Effect extends Comparable<Effect> {

	/**
	 * warning: hacky
	 * 
	 * @return low numbers indicate that the takeEffect should process early - high numbers indicate that the takeEffect should proces later 
	 */
	int getPriorityIndex();
	
	
	/**
	 * performs calculations on each tick, such as decreasing the remaining time
	 */
	void performTick();

	/**
	 * @return if 0, the effect will be removed
	 */
	int remainingTicks();

	/**
	 * @param character to be manipulated
	 */
	void takeEffect(Character character);
}
