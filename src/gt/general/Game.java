package gt.general;

public interface Game {

	/**
	 * ensures there are no internal dependencies to prevent the game from garbage collection
	 * e.g. removes related tasks from the scheduler
	 */
	void dispose();
	
}
