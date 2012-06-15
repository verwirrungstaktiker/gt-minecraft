package gt.lastgnome;

import gt.general.Game;
import gt.general.GameManager;
import gt.general.character.Hero;
import gt.general.character.Team;
import gt.general.character.TeamManager;
import gt.general.world.WorldInstance;
import gt.plugin.helloworld.HelloWorld;

import org.bukkit.World;

public class LastGnomeGameManager extends GameManager {

	private final LastGnomeWorldManager worldManager;
	
	/**
	 * @param initialWorld the base world where the initial spawnpoint is
	 * @param teamManager the global TeamManager
	 */
	public LastGnomeGameManager(final World initialWorld, final TeamManager teamManager) {
		super(initialWorld, teamManager);
		
		worldManager = new LastGnomeWorldManager(initialWorld);
	}

	/**
	 * @param team Who plays the game.
	 * @param worldName Where is the Game played.
	 * @return The instantiated Game.
	 */
	public LastGnomeGame startGame(final Team team, final String worldName) {
		// instantiate
		LastGnomeGame lastGnomeGame = new LastGnomeGame(team);
		
		// build world
		lastGnomeGame.setWorldWrapper(worldManager.instantiateWorld(worldName));
		
		// generic
		startGame(lastGnomeGame);
		
		// gui 
		for(Hero hero : team.getPlayers()) {
			lastGnomeGame.upgradeGui(hero);
		}
		
		// TODO actually do something
		new TeamLostTrigger(lastGnomeGame, null, HelloWorld.getTriggerManager());
		
		HelloWorld.registerListener(lastGnomeGame);
		
		getRunningGames().add(lastGnomeGame);
		return lastGnomeGame;
	}
	
	@Override
	public void endGame(final Game game) {
		WorldInstance world = game.getWorldInstance();
		
		super.endGame(game);
		
		worldManager.disposeWorldInstance(world);		
	}
	
}
