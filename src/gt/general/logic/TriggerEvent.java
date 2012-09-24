/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic;

import org.bukkit.entity.Player;

public class TriggerEvent {

	private final Player player;
	private final boolean active;

	/**
	 * Construct a new TriggerEvent
	 * @param active true if active
	 * @param player the corresponding bukkit Player
	 */
	public TriggerEvent(final boolean active, final Player player) {
		this.active = active;
		this.player = player;
	}


	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}


	/**
	 * @return the newState
	 */
	public boolean isActive() {
		return active;
	}
	
	
}
