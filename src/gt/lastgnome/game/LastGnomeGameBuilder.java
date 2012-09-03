package gt.lastgnome.game;

import gt.general.RespawnManager;
import gt.general.character.Hero;
import gt.general.character.Team;
import gt.general.character.ZombieManager;
import gt.general.logic.TriggerContext;
import gt.general.logic.TriggerManager;
import gt.general.logic.persistence.exceptions.PersistenceException;
import gt.general.logic.response.Response;
import gt.general.logic.response.ZombieResponse;
import gt.general.world.WorldManager;
import gt.lastgnome.LeverRandomizer;
import gt.lastgnome.MathRiddleRandomizer;
import gt.lastgnome.scoring.ScoreManager;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class LastGnomeGameBuilder extends AbstractLastGnomeGameBuilder {

	private LastGnomeGame game;
	private final Team team;
	private RespawnManager respawnManager;
	
	/**
	 * @param team team that starts a new game
	 */
	public LastGnomeGameBuilder(final Team team) {
		this.team = team;
	}
	
	@Override
	public void instantiateGame() {
		game = new LastGnomeGame(team);
		
		setupScoreManager();
		
		team.fix();
	}

	@Override
	public void buildWorldInstance(final WorldManager worldManager, final String worldName) throws PersistenceException {
		super.buildWorldInstance(worldManager, worldName);
		
		TriggerManager triggerManager = new TriggerManager();
		
		
		game.getWorldInstance().init(triggerManager);
		
		// TODO connect this to the game, so it can be stopped when the game is finished
		respawnManager = new RespawnManager(team, 
											triggerManager,
											game.getWorldInstance().getSpawn());		
	}

	@Override
	public void startGame() {
		TriggerManager triggerManager = game.getWorldInstance().getTriggerManager();
		
		MathRiddleRandomizer mathRandomizer = new MathRiddleRandomizer(triggerManager);
		mathRandomizer.randomizeMathRiddles();
		
		LeverRandomizer leverRandomizer = new LeverRandomizer(triggerManager);
		leverRandomizer.randomizeLevers();
		
		game.registerListener(game);

		setupZombieManager(triggerManager);
		setupAllHeroes(triggerManager);

		game.registerListener(respawnManager);
		game.setRespawnManager(respawnManager);
	}

	private void setupAllHeroes(final TriggerManager triggerManager) {		
		game.getWorldInstance()
			.getSpawn()
			.spawnTeam(game.getTeam());
		
		for(Hero hero : team.getPlayers()) {
			game.upgradeGui(hero);
		}	
		
		//Set on correct GameMode and ensure empty inventory
		for (Hero hero: game.getTeam().getPlayers()) {
			Player p = hero.getPlayer();
			p.setGameMode(GameMode.SURVIVAL);
			p.getInventory().clear();
			//Make sure player is in good health and not too hungry
			p.setHealth(p.getMaxHealth());
			p.setFoodLevel(20);
		}
	}
	
	@Override
	protected AbstractLastGnomeGame getAbstractGame() {
		return game;
	}	
	
	/**
	 * Score Manager setup
	 */
	private void setupScoreManager() {
		ScoreManager sm = new ScoreManager(game);
		game.setScoreManager(sm);
		game.registerListener(sm);
	}

	/**
	 * ZombieManager setup
	 * @param triggerManager the game's triggerManager
	 */
	private void setupZombieManager(final TriggerManager triggerManager) {
		ZombieManager zombieManager = game.getZombieManager();
		game.registerSyncTask(zombieManager, 0, ZombieManager.SCHEDULE_RATE);
		
		//Give all ZombieResponses the ZombieManager
		for (TriggerContext tc : triggerManager.getTriggerContexts()) {
			for (Response response : tc.getResponses()) {
				if (response instanceof ZombieResponse) {
					((ZombieResponse) response).setZombieManager(zombieManager);
				}
			}
		}
	}

}
