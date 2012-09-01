package gt.general;

import gt.general.character.TeamManager;
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

	
	private final TeamManager teamManager;
	
	private final WorldManager worldManager;
	
	/**
	 * @return the worldManager
	 */
	public WorldManager getWorldManager() {
		return worldManager;
	}

	/**
	 * @param worldManager the global WorldManager
	 * @param teamManager the global TeamManager
	 */
	public GameManager(final WorldManager worldManager, final TeamManager teamManager) {
		runningGames = new HashSet<Game>();
		
		this.worldManager = worldManager;
		this.teamManager = teamManager;
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
			// TODO Auto-generated catch block
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
			// TODO remove from list
		}
	}

}
