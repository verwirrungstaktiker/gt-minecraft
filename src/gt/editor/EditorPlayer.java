package gt.editor;

import static org.bukkit.ChatColor.*;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import gt.editor.event.ContextSwitchEvent;
import gt.editor.event.ParticleSuppressEvent;
import gt.general.logic.TriggerContext;
import gt.plugin.meta.Hello;

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
		switchContext(context);
	}
	
	public void exitContext() {		
		triggerState = TriggerState.IDLE;
		switchContext(NO_CONTEXT);
	}
	
	private void switchContext(final TriggerContext to) {
		TriggerContext from = getActiveContext();
		
		activeContext = to;

		Hello.callEvent(new ContextSwitchEvent(player, from, to));
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
	public void setSuppressHighlight(boolean suppressHighlight) {
		
		if(this.suppressHighlight != suppressHighlight) {
		
			this.suppressHighlight = suppressHighlight;
			Hello.callEvent(new ParticleSuppressEvent(player, suppressHighlight));
			
		}
	}
}
