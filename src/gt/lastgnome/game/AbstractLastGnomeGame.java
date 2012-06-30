package gt.lastgnome.game;

import gt.general.Game;
import gt.general.character.Team;
import gt.general.character.ZombieManager;
import gt.lastgnome.GnomeSocketEnd;
import gt.lastgnome.GnomeSocketStart;

import org.bukkit.entity.Player;

public abstract class AbstractLastGnomeGame extends Game{

	private ZombieManager zombieManager;
	
	private GnomeSocketStart startSocket;
	private GnomeSocketEnd endSocket;

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

	/**
	 * @return the startSocket
	 */
	public GnomeSocketStart getStartSocket() {
		return startSocket;
	}

	/**
	 * @param startSocket the startSocket to set
	 */
	public void setStartSocket(GnomeSocketStart startSocket) {
		this.startSocket = startSocket;
	}

	/**
	 * @return the endSocket
	 */
	public GnomeSocketEnd getEndSocket() {
		return endSocket;
	}

	/**
	 * @param endSocket the endSocket to set
	 */
	public void setEndSocket(GnomeSocketEnd endSocket) {
		this.endSocket = endSocket;
	}
}
