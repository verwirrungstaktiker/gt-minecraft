package gt.editor.event;

import gt.general.logic.TriggerContext;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ContextSwitchEvent extends Event {

	private final TriggerContext oldContext;
	private final TriggerContext newContext;
	
	private final Player player;
	
	
	public ContextSwitchEvent(final Player player, final TriggerContext oldContext, final TriggerContext newContext) {
		this.player = player;
		this.oldContext = oldContext;
		this.newContext = newContext;
	}
	
	/**
	 * @return the oldContext
	 */
	public TriggerContext getOldContext() {
		return oldContext;
	}

	/**
	 * @return the newContext
	 */
	public TriggerContext getNewContext() {
		return newContext;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	private static final HandlerList HANDLERS = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
	
	
}
