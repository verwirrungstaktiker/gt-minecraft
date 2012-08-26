package gt.general.logic.persistence.exceptions;

public class PersistenceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2863169802703883238L;
	
	private final String key;
	
	public PersistenceException(final String key) {
		super();
		this.key = key;
	}

	
	public String getKey() {
		return key;
	}
}
