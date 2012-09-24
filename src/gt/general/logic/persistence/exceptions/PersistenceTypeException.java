/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic.persistence.exceptions;

public class PersistenceTypeException extends PersistenceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4514866240392932060L;

	/**
	 * construct a new exception
	 * @param key exception key
	 */
	public PersistenceTypeException(final String key) {
		super(key);
	}

}
