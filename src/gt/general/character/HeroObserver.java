/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation f�r kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ne� (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.character;


public interface HeroObserver {

	/**
	 * To be called if the Hero is changed.
	 * 
	 * @param hero The Hero to be observed.
	 * @param notification The type of change which happened.
	 */
	void update(final Hero hero, final Hero.Notification notification);
	
}
