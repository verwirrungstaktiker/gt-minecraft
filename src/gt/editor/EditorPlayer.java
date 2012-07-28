package gt.editor;

import static org.bukkit.ChatColor.*;
import gt.editor.event.LogicSelectionEvent;
import gt.editor.event.HighlightSuppressEvent;
import gt.general.logic.TriggerContext;
import gt.general.logic.persistance.YamlSerializable;
import gt.plugin.meta.Hello;

import org.bukkit.entity.Player;

public class EditorPlayer {

	private final static TriggerContext NO_CONTEXT = null;

	public enum TriggerState {
		IDLE,		// no triggercontext
		TRIGGER,
		RESPONSE,
		STANDBY		// triggercontext but standby
	}
	
	private final Player player;

	private TriggerState triggerState;
	private TriggerContext activeContext;
	
	private YamlSerializable selectedItem;
	
	private boolean suppressHighlight;
	
	
	public EditorPlayer (final Player player) {
		this.player = player;
		
		triggerState = TriggerState.IDLE;
		activeContext = NO_CONTEXT;
		
		setSuppressHighlight(false);
	}


	/**
	 * @return the state
	 */
	public TriggerState getTriggerState() {
		return triggerState;
	}


	/**
	 * @param triggerState the state to set
	 */
	public void setTriggerState(final TriggerState triggerState) {
		this.triggerState = triggerState;
	}

	public void toggleTriggerState() {
	
		switch (triggerState) {
		case TRIGGER:
			setTriggerState(TriggerState.RESPONSE);
			return;
			
		case RESPONSE:
			setTriggerState(TriggerState.STANDBY);
			
			//loadInventory(player);
			return;
			
		case STANDBY:
			setTriggerState(TriggerState.TRIGGER);
			return;
			
		default:
			player.sendMessage(YELLOW + "No active TriggerContext.");
			break;
		}		
	}
	

	/**
	 * @return the activeContext
	 */
	public TriggerContext getActiveContext() {
		return activeContext;
	}


	/**
	 * @param activeContext the activeContext to set
	 */
	public void enterContext(final TriggerContext context) {
		triggerState = TriggerState.TRIGGER;
		activeContext = context;
		
		Hello.callEvent(new LogicSelectionEvent(player));
	}
	
	public void exitContext() {		
		triggerState = TriggerState.IDLE;
		activeContext = NO_CONTEXT;
		
		Hello.callEvent(new LogicSelectionEvent(player));
	}
	
	
	public boolean isInContext(final TriggerContext other) {
		return activeContext == other;
	}
	
	public boolean hasActiveContext() {
		return activeContext != NO_CONTEXT;
	}
	
	public void sendMessage(final String msg) {
		player.sendMessage(msg);
	}
	
	
	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}


	/**
	 * @return the suppressHighlight
	 */
	public boolean isSuppressHighlight() {
		return suppressHighlight;
	}


	/**
	 * @param suppressHighlight the suppressHighlight to set
	 */
	public void setSuppressHighlight(final boolean suppressHighlight) {
		this.suppressHighlight = suppressHighlight;
		Hello.callEvent(new HighlightSuppressEvent(player, suppressHighlight));
	}


	/**
	 * @return the selectedItem
	 */
	public YamlSerializable getSelectedItem() {
		return selectedItem;
	}


	/**
	 * @param selectedItem the selectedItem to set
	 */
	public void setSelectedItem(YamlSerializable selectedItem) {
		this.selectedItem = selectedItem;
		Hello.callEvent(new LogicSelectionEvent(player));
	}
}
