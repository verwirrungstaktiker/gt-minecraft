package gt.editor;

/**
 * basic observer: semi-pull model
 * 
 * @author Sebastian Fahnenschreiber
 */
public interface LogicObserver {

	public enum Observee{
		/** a trigger context changed */
		TRIGGER_MANAGER,
		
		/** a trigger or a response changed */
		TRIGGER_CONTEXT
	}
	
	/**
	 * to be called, if something changed
	 * 
	 * @param type what changed
	 * @param what the concrete object that changed (intended for == comparison)
	 */
	void update(Observee type, Object what);
}
