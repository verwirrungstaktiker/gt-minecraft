package gt.lastgnome.game;

import gt.general.character.Hero;
import gt.general.character.Team;
import gt.general.character.ZombieManager;
import gt.general.logic.TriggerContext;
import gt.general.logic.TriggerManager;
import gt.general.logic.persistance.exceptions.PersistanceException;
import gt.general.logic.response.Response;
import gt.general.logic.response.ZombieResponse;
import gt.general.world.WorldManager;
import gt.lastgnome.MathRiddleRandomizer;
import gt.plugin.meta.Hello;
import gt.plugin.meta.MultiListener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

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
		
		ScoreManager sm = new ScoreManager();
		game.setScoreManager(sm);
		sm.setGame(game);
		MultiListener.registerListener(sm);
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
		
		MultiListener.registerListener(zombieManager);
		
		//TODO Its a bit Hacky
		for (TriggerContext tc : triggerManager.getTriggerContexts()) {
			for (Response response : tc.getResponses()) {
				if (response instanceof ZombieResponse) {
					((ZombieResponse) response).setZombieManager(zombieManager);
				}
			}
		}
		
		game.getWorldInstance()
			.getSpawn()
			.spawnTeam(game.getTeam());
		
		//Set on correct GameMode and ensure empty inventory
		for (Hero hero: game.getTeam().getPlayers()) {
			Player p = hero.getPlayer();
			p.setGameMode(GameMode.SURVIVAL);
			p.getInventory().clear();
			//Make sure player is in good health
			p.setHealth(p.getMaxHealth());
		}
		
	}

	@Override
	protected AbstractLastGnomeGame getAbstractGame() {
		return game;
	}	

}
