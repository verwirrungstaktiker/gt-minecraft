package gt.lastgnome;

import gt.general.GameManager;
import gt.general.Hero;
import gt.general.Team;
import gt.plugin.helloworld.HelloWorld;

import org.bukkit.World;

public class LastGnomeGameManager extends GameManager {

	/**
	 * @param initialWorld the base world where the initial spawnpoint is
	 */
	public LastGnomeGameManager(final World initialWorld) {
		super(initialWorld);
	}

	/**
	 * @param team Who plays the game.
	 * @param initialBearer Who carries the Gnome initially.
	 * @param world Where is the Game played.
	 * @return The instantiated Game.
	 */
	public LastGnomeGame startGame(final Team team, final Hero initialBearer, final World world) {
		// instantiate
		LastGnomeGame lastGnomeGame = new LastGnomeGame(team, initialBearer);
		
		// generic
		startGame(lastGnomeGame, world);
		
		// gui 
		for(Hero hero : team.getPlayers()) {
			lastGnomeGame.upgradeGui(hero);
		}
		
		// TODO actually do something
		new TeamLostTrigger(lastGnomeGame, null, HelloWorld.getTM());
		
		return lastGnomeGame;
	}
	
}
