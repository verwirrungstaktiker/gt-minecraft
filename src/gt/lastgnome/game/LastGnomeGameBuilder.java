package gt.lastgnome.game;

import gt.general.character.Hero;
import gt.general.character.Team;
import gt.general.character.ZombieManager;
import gt.general.logic.TriggerContext;
import gt.general.logic.TriggerManager;
import gt.general.logic.persistance.exceptions.PersistanceException;
import gt.general.logic.response.Response;
import gt.general.logic.response.ZombieSpawnResponse;
import gt.general.world.WorldManager;
import gt.lastgnome.MathRiddleRandomizer;
import gt.plugin.meta.Hello;
import gt.plugin.meta.MultiListener;

public class LastGnomeGameBuilder extends AbstractLastGnomeGameBuilder {

	private LastGnomeGame game;
	private final Team team;
	
	/**
	 * @param team team that starts a new game
	 */
	public LastGnomeGameBuilder(final Team team) {
		this.team = team;
	}
	
	@Override
	public void instantiateGame() {
		game = new LastGnomeGame(team);
	}

	@Override
	public void buildWorldInstance(final WorldManager worldManager, final String worldName) throws PersistanceException {
		super.buildWorldInstance(worldManager, worldName);
				
		game.getWorldInstance().init(new TriggerManager());
	}

	@Override
	public void startGame() {
		TriggerManager triggerManager = game.getWorldInstance().getTriggerManager();
		
		
		MathRiddleRandomizer randomizer = new MathRiddleRandomizer(triggerManager);
		randomizer.randomizeMathRiddles();
		
		for(Hero hero : team.getPlayers()) {
			game.upgradeGui(hero);
		}	
		
		MultiListener.registerListener(game);

		ZombieManager zombieManager = game.getZombieManager();
		int id = Hello.scheduleSyncTask(zombieManager, 0, 10);
		zombieManager.setTaskID(id);
		
		//TODO Its a bit Hacky
		for (TriggerContext tc : triggerManager.getTriggerContexts()) {
			for (Response response : tc.getResponses()) {
				if (response instanceof ZombieSpawnResponse) {
					((ZombieSpawnResponse) response).setZombieManager(zombieManager);
				}
			}
		}
	    
		// TODO actually do something
		//new TeamLostTrigger(lastGnomeGame, HelloWorld.getTriggerManager());
		
		game.getWorldInstance()
			.getSpawn()
			.spawnTeam(game.getTeam());
		
	}

	@Override
	protected AbstractLastGnomeGame getAbstractGame() {
		return game;
	}	

}
