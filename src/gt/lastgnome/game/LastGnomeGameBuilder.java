package gt.lastgnome.game;

import gt.general.character.Hero;
import gt.general.character.Team;
import gt.general.character.ZombieManager;
import gt.general.trigger.TriggerManager;
import gt.general.world.WorldManager;
import gt.plugin.Hello;
import gt.plugin.listener.MultiListener;

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
	public void buildGui() {
		for(Hero hero : team.getPlayers()) {
			game.upgradeGui(hero);
		}		
	}

	@Override
	public void startGame() {

		MultiListener.registerListener(game);

		ZombieManager zombieManager = game.getZombieManager();
		int id = Hello.ScheduleSyncTask(zombieManager, 0, 10);
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
