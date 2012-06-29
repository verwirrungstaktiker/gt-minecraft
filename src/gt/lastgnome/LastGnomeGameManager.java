package gt.lastgnome;

import gt.general.Game;
import gt.general.GameManager;
import gt.general.character.Hero;
import gt.general.character.Team;
import gt.general.character.TeamManager;
import gt.general.character.ZombieManager;
import gt.general.trigger.TriggerManager;
import gt.general.world.WorldInstance;
import gt.plugin.Hello;
import gt.plugin.listener.MultiListener;

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
		
		LastGnomeWorldInstance worldInstance = worldManager.instantiateWorld(worldName);
		
		
		ZombieManager zombieManager = new ZombieManager(worldInstance.getWorld());
		//geht das besser? 
		int id = Hello.ScheduleSyncTask(zombieManager, 0, 10);
		zombieManager.setTaskID(id);
		
		// instantiate
		LastGnomeGame lastGnomeGame = new LastGnomeGame(team, zombieManager);
		
		// build world
		lastGnomeGame.setWorldWrapper(worldInstance);
		
		// generic
		startGame(lastGnomeGame);
		
		// gui 
		for(Hero hero : team.getPlayers()) {
			lastGnomeGame.upgradeGui(hero);
		}
		
		// TODO actually do something
		//new TeamLostTrigger(lastGnomeGame, HelloWorld.getTriggerManager());
		
		MultiListener.registerListener(lastGnomeGame);
		
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
