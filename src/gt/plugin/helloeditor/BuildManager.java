package gt.plugin.helloeditor;

import gt.general.trigger.TriggerContext;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BuildManager implements Listener {

	public enum TriggerState {
		IDLE,
		TRIGGER,
		RESPONSE
	}
	
	private Map<String, TriggerState> playerTriggerStates = new HashMap<String, TriggerState>();
	
	private Map<String, TriggerContext> playerTriggerContexts = new HashMap<String, TriggerContext>();
	
	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event) {
		String name = event.getPlayer().getName();
		
		addPlayer(name);
	}
	
	@EventHandler
	public void onPlayerLeave(final PlayerQuitEvent event) {
		String name = event.getPlayer().getName();
		
		removePlayer(name);
	}
	
	
	/**
	 * creates the necessary triggerrelevant hashmaps for a new player
	 * @param name name of the player
	 */
	private void addPlayer(final String name) {
		playerTriggerStates.put(name, TriggerState.IDLE);
		playerTriggerContexts.put(name, null);
	}

	private void removePlayer(final String name) {
		playerTriggerStates.remove(name);
		playerTriggerContexts.remove(name);
	}
	
	
}
