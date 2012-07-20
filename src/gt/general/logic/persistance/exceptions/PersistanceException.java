package gt.general.logic.persistance.exceptions;

public class PersistanceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2863169802703883238L;
	
	private final String key;
	
	public PersistanceException(final String key) {
		super();
		this.key = key;
	}

	
	public String getKey() {
		return key;
	}
}
