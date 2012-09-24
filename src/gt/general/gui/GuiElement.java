/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.gui;

import gt.general.character.Hero;

public interface GuiElement{
	
	/**
	 * @param hero to be called if attached to a Hero
	 */
	void attach(final Hero hero) ;
	
	
	/**
	 * @param hero to be called detached from a Hero
	 */
	void detach(final Hero hero);
}
