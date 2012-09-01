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
