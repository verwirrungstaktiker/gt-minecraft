package gt.editor.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LogicChangeEvent extends Event {

	public enum ObserveeParent{
		/** a trigger context changed */
		TRIGGER_MANAGER,
		
		/** a trigger or a response changed */
		TRIGGER_CONTEXT
	}
	
	private final ObserveeParent type;
	private final Object concrete;
	
	/**
	 * Construct new LogicChangeEvent
	 * @param type who has changed
	 * @param concrete the Object that has changed
	 */
	public LogicChangeEvent(final ObserveeParent type, final Object concrete) {
		this.type =  type;
		this.concrete = concrete;
	}

	/**
	 * @return the type
	 */
	public ObserveeParent getType() {
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
	
	/**
	 * @return the global HanderList
	 */
	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}
