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
