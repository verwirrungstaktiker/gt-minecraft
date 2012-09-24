/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation f�r kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ne� (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general;

public interface GameObserver {

	public enum EventType {
		GAMEEND
	}
	
	/**
	 * push style event
	 * 
	 * @param game Where it happened.
	 * @param type What happened.
	 */
	void update(Game game, EventType type);
}
