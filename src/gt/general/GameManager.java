package gt.general;

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
	
	/**
	 * @param initialWorld the world with the initial spawn
	 */
	public GameManager(final World initialWorld) {
		runningGames = new HashSet<Game>();
		
		this.initialWorld = initialWorld;
	}
	
	/**
	 * @param game The Game to be initialized.
	 * @param world The place of the Game.
	 */
	protected void startGame(final Game game, final World world) {
		game.setWorld(world);
		
		game.getTeam().teleportTo(world.getSpawnLocation());
		
		runningGames.add(game);
	}
	
	
	@Override
	public void update(final Game game, final EventType type) {
		if(type == GameObserver.EventType.GAMEEND) {
			endGame(game);
		}
		
	}
	
	/**
	 * @param game The game to be ended
	 */
	public void endGame(final Game game) {
		game.getTeam().teleportTo(initialWorld.getSpawnLocation());
		game.dispose();
		
	}
	
	/**
	 * ends all currently running games
	 */
	public void endAllGames() {
		for(Game game : runningGames) {
			endGame(game);
		}
	}
}
