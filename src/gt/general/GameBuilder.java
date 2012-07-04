package gt.general;

import gt.general.world.WorldManager;

public interface GameBuilder {

	/**
	 * after this step, the game should be instantiated .getGame() should no longer return null
	 */
	void instantiateGame();
	
	/**
	 * after this step, the world should be fully loaded 
	 * 
	 * @param worldManager the world manager to load the world
	 * @param worldName the name of the world
	 */
	void buildWorldInstance(WorldManager worldManager, String worldName);
	
	/**
	 * after this step, the game should be ready to be started
	 */
	void loadGameSpecific();
	
	/**
	 * after this final step, the game should be up and running
	 */
	void startGame();
	
	/**
	 * @return the produced game
	 */
	Game getGame();
}
