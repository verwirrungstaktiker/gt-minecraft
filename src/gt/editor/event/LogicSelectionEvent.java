/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.editor.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class LogicSelectionEvent extends PlayerEvent {

	/**
	 * Construct a new LogicSelectionEvent
	 * @param player the corresponding bukkit player
	 */
	public LogicSelectionEvent(final Player player) {
		super(player);
	}

	private static final HandlerList HANDLERS = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	/**
	 * @return the global HandlerList
	 */
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}
