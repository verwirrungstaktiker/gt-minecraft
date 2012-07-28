package gt.editor.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class HighlightSuppressEvent extends PlayerEvent {

	private final boolean suppressed;

	private static final HandlerList HANDLERS = new HandlerList();	
	
	/**
	 * @param who the player this event ist about
	 */
	public HighlightSuppressEvent(final Player who, final boolean suppressed) {
		super(who);
		this.suppressed = suppressed;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

	/**
	 * @return the suppressed
	 */
	public boolean isSuppressed() {
		return suppressed;
	}

}
