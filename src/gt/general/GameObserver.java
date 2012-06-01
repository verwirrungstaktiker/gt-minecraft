package gt.general;

public interface GameObserver {

	public enum EventType {
		GAMEEND
	}
	
	//push style event
	void update(Game game, EventType type);
}
