package gt.general;

import gt.general.character.TeamManager;
import gt.general.world.WorldInstance;
import gt.general.world.WorldManager;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.World;

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
	 * @param initialWorld the world with the initial spawn
	 * @param teamManager the global TeamManager
	 */
	public GameManager(final WorldManager worldManager, final TeamManager teamManager) {
		runningGames = new HashSet<Game>();
		
		this.worldManager = worldManager;
		this.teamManager = teamManager;
	}
	
	
	public Game startGame(final GameBuilder builder, final String worldName) {
		
		builder.instantiateGame();
		
		builder.buildWorldInstance(worldManager, worldName);
		
		builder.loadGameSpecific();
		
		builder.finalizeGame();
		
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
		
		// TODO make this better
		game.getTeam()
			.teleportTo(worldManager.getInitialWorld()
									.getSpawnLocation());
		
		teamManager.disband(game.getTeam());

		
		// TODO
		WorldInstance world = game.getWorldInstance();
		worldManager.disposeWorldInstance(world);	
		
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
