package gt.lastgnome;

import gt.general.GameManager;
import gt.general.character.Hero;
import gt.general.character.Team;
import gt.plugin.helloworld.HelloWorld;

import org.bukkit.World;

public class LastGnomeGameManager extends GameManager {

	private final LastGnomeWorldManager worldManager;
	
	/**
	 * @param initialWorld the base world where the initial spawnpoint is
	 */
	public LastGnomeGameManager(final World initialWorld) {
		super(initialWorld);
		
		worldManager = new LastGnomeWorldManager(initialWorld);
	}

	/**
	 * @param team Who plays the game.
	 * @param initialBearer Who carries the Gnome initially.
	 * @param worldName Where is the Game played.
	 * @return The instantiated Game.
	 */
	public LastGnomeGame startGame(final Team team, final Hero initialBearer, final String worldName) {
		// instantiate
		LastGnomeGame lastGnomeGame = new LastGnomeGame(team, initialBearer);
		
		// build world
		lastGnomeGame.setWorldWrapper(worldManager.instantiateWorld(worldName));
		
		// generic
		startGame(lastGnomeGame);
		
		// gui 
		for(Hero hero : team.getPlayers()) {
			lastGnomeGame.upgradeGui(hero);
		}
		
		// TODO actually do something
		new TeamLostTrigger(lastGnomeGame, null, HelloWorld.getTM());
		
		HelloWorld.registerListener(lastGnomeGame);
		
		getRunningGames().add(lastGnomeGame);
		return lastGnomeGame;
	}
	
}
