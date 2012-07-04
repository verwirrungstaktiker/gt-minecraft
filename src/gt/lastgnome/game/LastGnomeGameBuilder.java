package gt.lastgnome.game;

import gt.general.character.Hero;
import gt.general.character.Team;
import gt.general.character.ZombieManager;
import gt.general.trigger.TriggerManager;
import gt.general.world.WorldManager;
import gt.plugin.meta.Hello;
import gt.plugin.meta.MultiListener;

public class LastGnomeGameBuilder extends AbstractLastGnomeGameBuilder {

	private LastGnomeGame game;
	private final Team team;
	
	public LastGnomeGameBuilder(final Team team) {
		this.team = team;
	}
	
	@Override
	public void instantiateGame() {
		game = new LastGnomeGame(team);
	}

	@Override
	public void buildWorldInstance(final WorldManager worldManager, final String worldName) {
		super.buildWorldInstance(worldManager, worldName);
				
		game.getWorldInstance().init(new TriggerManager());
	}

	@Override
	public void startGame() {

		for(Hero hero : team.getPlayers()) {
			game.upgradeGui(hero);
		}	
		
		MultiListener.registerListener(game);

		ZombieManager zombieManager = game.getZombieManager();
		int id = Hello.scheduleSyncTask(zombieManager, 0, 10);
		zombieManager.setTaskID(id);
		
	
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
