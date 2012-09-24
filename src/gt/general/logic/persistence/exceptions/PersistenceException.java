/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation f�r kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ne� (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic.persistence.exceptions;

public class PersistenceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2863169802703883238L;
	
	private final String key;
	
	/**
	 * @param key exception key
	 */
	public PersistenceException(final String key) {
		super();
		this.key = key;
	}

	/**
	 * @return exception key
	 */
	public String getKey() {
		return key;
	}
}
