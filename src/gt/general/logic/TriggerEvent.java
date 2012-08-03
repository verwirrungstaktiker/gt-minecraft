package gt.general.logic;

import org.bukkit.entity.Player;

public class TriggerEvent {

	private final Player player;
	private final boolean active;

	
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
