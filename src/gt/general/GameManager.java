package gt.general;

import gt.general.character.TeamManager;
import gt.general.world.WorldInstance;

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

	/** The world where the players spawn initially */
	private final World initialWorld;
	
	private final TeamManager teamManager;
	
	/**
	 * @param initialWorld the world with the initial spawn
	 */
	public GameManager(final World initialWorld, final TeamManager teamManager) {
		runningGames = new HashSet<Game>();
		
		this.initialWorld = initialWorld;
		this.teamManager = teamManager;
	}
	
	/**
	 * @param game The Game to be initialized.
	 */
	protected void startGame(final Game game) {
		WorldInstance world = game.getWorldInstance();
		game.getTeam().teleportTo(world.getSpawnLocation());
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
		game.getTeam().teleportTo(initialWorld.getSpawnLocation());
		teamManager.disband(game.getTeam());
		
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
