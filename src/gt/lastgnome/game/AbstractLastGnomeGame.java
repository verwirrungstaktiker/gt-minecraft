package gt.lastgnome.game;

import gt.general.Game;
import gt.general.character.Team;
import gt.general.character.ZombieManager;

import org.bukkit.entity.Player;

public abstract class AbstractLastGnomeGame extends Game{

	private ZombieManager zombieManager;

	public AbstractLastGnomeGame(Team team) {
		super(team);
	}
	
	public abstract void onStartSocketInteract(Player player);
	
	
	public abstract void onEndSocketInteract(Player player);

	/**
	 * @return this game's ZombieManager
	 */
	public ZombieManager getZombieManager() {
		return zombieManager;
	}
	
	public void setZombieManager(final ZombieManager zombieManager) {
		this.zombieManager = zombieManager;
	}

}
