/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general;

import gt.general.logic.persistence.exceptions.PersistenceException;
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
	 * @throws PersistenceException 
	 */
	void buildWorldInstance(WorldManager worldManager, String worldName) throws PersistenceException;
	
	/**
	 * after this step, the game should be ready to be started
	 * @throws PersistenceException 
	 */
	void loadGameSpecific() throws PersistenceException;
	
	/**
	 * after this final step, the game should be up and running
	 */
	void startGame();
	
	/**
	 * @return the produced game
	 */
	Game getGame();
}
