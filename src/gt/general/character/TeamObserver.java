/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.character;

public interface TeamObserver {

	/**
	 * To be called if the Team is changed
	 * 
	 * @param team The Team to be observed.
	 * @param notification The type of change which happened.
	 */
	void update(Team team, Team.Notification notification);
}
