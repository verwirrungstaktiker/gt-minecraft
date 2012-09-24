/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.lastgnome.game;

import gt.general.Game;
import gt.general.character.Hero;
import gt.general.character.Team;
import gt.general.character.ZombieManager;
import gt.lastgnome.GnomeSocketEnd;
import gt.lastgnome.GnomeSocketStart;
import gt.plugin.meta.Hello;
import gt.plugin.meta.MultiListener;

import org.bukkit.World;
import org.bukkit.entity.Player;

public abstract class AbstractLastGnomeGame extends Game{

	private ZombieManager zombieManager;
	
	private GnomeSocketStart startSocket;
	private GnomeSocketEnd endSocket;

	/**
	 * @param team team that will play the game
	 */
	public AbstractLastGnomeGame(final Team team) {
		super(team);
	}
	
	/**
	 * player interacts with StartSocket
	 * @param player bukkit player
	 */
	public abstract void onStartSocketInteract(Player player);
	
	/**
	 * player interacts with EndSocket
	 * @param player bukkit player
	 */
	public abstract void onEndSocketInteract(Player player);

	/**
	 * @return this game's ZombieManager
	 */
	public ZombieManager getZombieManager() {
		return zombieManager;
	}
	
	/**
	 * @param zombieManager the used ZombieManager
	 */
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
	public void setStartSocket(final GnomeSocketStart startSocket) {
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
	public void setEndSocket(final GnomeSocketEnd endSocket) {
		this.endSocket = endSocket;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		startSocket.dispose();
		endSocket.dispose();
		//MultiListener.unregisterListener(this);
		//MultiListener.unregisterListener(this.zombieManager);
		World startWorld = Hello.getPlugin().getServer().getWorld("world");
	}
}
