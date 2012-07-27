package gt.editor.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LogicChangeEvent extends Event {

	public enum ObserveeType{
		/** a trigger context changed */
		TRIGGER_MANAGER,
		
		/** a trigger or a response changed */
		TRIGGER_CONTEXT
	}
	
	private final ObserveeType type;
	private final Object concrete;
	
	public LogicChangeEvent(final ObserveeType type, final Object concrete) {
		this.type =  type;
		this.concrete = concrete;
	}

	/**
	 * @return the type
	 */
	public ObserveeType getType() {
		return type;
	}

	/**
	 * @return the concrete
	 */
	public Object getConcrete() {
		return concrete;
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
