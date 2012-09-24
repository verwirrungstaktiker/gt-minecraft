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

import java.util.HashSet;
import java.util.Set;

/**
 * Implements common functionality for managing game modes.
 * 
 * @author Sebastian Fahnenschreiber
 */
public class GameManager implements GameObserver{

	private final Set<Game> runningGames;
	
	private final WorldManager worldManager;
	
	/**
	 * @return the worldManager
	 */
	public WorldManager getWorldManager() {
		return worldManager;
	}

	/**
	 * @param worldManager the global WorldManager
	 */
	public GameManager(final WorldManager worldManager) {
		runningGames = new HashSet<Game>();
		
		this.worldManager = worldManager;
	}
	
	
	/**
	 * instantiates a new game and starts it
	 * 
	 * @param builder utilized to instantiate the game
	 * @param worldName the world, where the game should take place
	 * @return the recently started game
	 */
	public Game startGame(final GameBuilder builder, final String worldName) {
		
		builder.instantiateGame();
		try {
			builder.buildWorldInstance(worldManager, worldName);
			builder.loadGameSpecific();
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
			
		builder.startGame();
		
		Game game = builder.getGame();
		runningGames.add(game);		
		return game;
	}
	
	
	@Override
	public void update(final Game game, final EventType type) {
		if(type == GameObserver.EventType.GAMEEND) {
			endGame(game);
		}
		
	}

	/**
	 * @return the runningGames
	 */
	public Set<Game> getRunningGames() {
		return runningGames;
	}
	
	/**
	 * @param game The game to be ended
	 */
	public void endGame(final Game game) {
		
		game.getTeam()
			.teleportTo(worldManager.getInitialWorld()
									.getSpawnLocation());
		game.getTeam().dispose();
		game.dispose();
	}
	
	/**
	 * ends all currently running games
	 */
	public void endAllGames() {
		for(Game game : runningGames) {
			endGame(game);
		}
		
		runningGames.clear();
		
	}

	/**
	 * toggle the pause function for all running games
	 */
	public void togglePauseAllGames() {
		for(Game game : runningGames) {
			game.toggleForcePause();
		}
	}

}
