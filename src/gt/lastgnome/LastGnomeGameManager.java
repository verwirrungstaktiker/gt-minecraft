package gt.lastgnome;

import gt.general.Game;
import gt.general.GameManager;
import gt.general.character.Hero;
import gt.general.character.Team;
import gt.general.character.TeamManager;
import gt.general.character.ZombieManager;
import gt.general.trigger.TriggerManager;
import gt.general.world.WorldInstance;
<<<<<<< Updated upstream
import gt.plugin.Hello;
=======
import gt.general.world.WorldManager;
import gt.plugin.helloworld.HelloWorld;
>>>>>>> Stashed changes
import gt.plugin.listener.MultiListener;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.getspout.spoutapi.SpoutManager;

public class LastGnomeGameManager extends GameManager {

	private final WorldManager worldManager;
	
	/**
	 * @param initialWorld the base world where the initial spawnpoint is
	 * @param teamManager the global TeamManager
	 */
	public LastGnomeGameManager(final World initialWorld, final TeamManager teamManager) {
		super(initialWorld, teamManager);
		
		worldManager = new WorldManager(initialWorld);
	}

	/**
	 * @param team Who plays the game.
	 * @param worldName Where is the Game played.
	 * @return The instantiated Game.
	 */
	public LastGnomeGame startGame(final Team team, final String worldName) {
		
		WorldInstance worldInstance = worldManager.instantiateWorld(worldName);
		
		
		ZombieManager zombieManager = new ZombieManager(worldInstance.getWorld());
		//geht das besser? 
		int id = Hello.ScheduleSyncTask(zombieManager, 0, 10);
		zombieManager.setTaskID(id);
		
		// instantiate
		LastGnomeGame lastGnomeGame = new LastGnomeGame(team, zombieManager);
		
		// build world
		lastGnomeGame.setWorldWrapper(worldInstance);
		
		Block s = worldInstance.getWorld().getBlockAt(-5, 66, 15);
		Block e = worldInstance.getWorld().getBlockAt(86, 66, 15);
		
		System.out.println(SpoutManager.getMaterialManager().overrideBlock(s, new GnomeSocketStart()));
		System.out.println(SpoutManager.getMaterialManager().overrideBlock(e, new GnomeSocketEnd()));
		
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
