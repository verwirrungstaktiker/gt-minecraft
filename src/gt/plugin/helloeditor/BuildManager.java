package gt.plugin.helloeditor;

import gt.general.trigger.TriggerContext;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.keyboard.Keyboard;


public class BuildManager implements Listener {

	public enum TriggerState {
		IDLE,
		TRIGGER,
		RESPONSE,
		STANDBY
	}
	
	/** contains all player's current TriggerStates **/
	private Map<String, TriggerState> playerTriggerStates = new HashMap<String, TriggerState>();
	/** contains all player's current TriggerContext's **/
	private Map<String, TriggerContext> playerTriggerContexts = new HashMap<String, TriggerContext>();
	
	
	/**
	 * handles key presses
	 * @param event player presses a key
	 */
	@EventHandler
	public void handleKeyPresses(final KeyPressedEvent event) {
		Player player = event.getPlayer();

		if(event.getKey() == Keyboard.KEY_F6) {
			toggleContext(player);
		}
		if(event.getKey() == Keyboard.KEY_F7) {
			toggleTriggerState(player);
		}
		if(event.getKey() == Keyboard.KEY_F9) {
			toggleContextInputFunction(player);
		}
		if(event.getKey() == Keyboard.KEY_F12) {
			cancelContext(player);
		}
	}
	
	/**
	 * register Triggers and Responses to playerTriggerContexts
	 * @param event player places a block
	 */
	@EventHandler
	public void registerTriggerContext(final BlockPlaceEvent event) {
		Player player = event.getPlayer();
		String name = player.getName();
		Block block = event.getBlockPlaced();
		// Trigger
		if(playerTriggerStates.get(name) == TriggerState.TRIGGER && isUsableAsTrigger(block)) {
			addTrigger(name, block);
		}
		// Response
		if(playerTriggerStates.get(name) == TriggerState.RESPONSE && isUsableAsResponse(block)) {
			addResponse(name, block);
		}

	}


	private boolean isUsableAsTrigger(Block block) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean isUsableAsResponse(Block block) {
		// TODO Auto-generated method stub
		return false;
	}

	private void addResponse(String name, Block block) {
		// TODO Auto-generated method stub
	}

	private void addTrigger(String name, Block block) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * toggle a players input function
	 * @param player bukkit player
	 */
	private void toggleContextInputFunction(final Player player) {
		String name = player.getName();
		if(playerTriggerContexts.get(name) != null) {
			playerTriggerContexts.get(name).toggleInputFunction();
			player.sendMessage(
					ChatColor.YELLOW + 
					"Trigger Input Fuction: " + 
					playerTriggerContexts.get(name).getInputFunction().toString());
		} else {
			player.sendMessage(ChatColor.YELLOW + "No active TriggerContext");
		}
	}

	/**
	 * Toggle context enter/leave
	 * @param player bukkit player
	 */
	private void toggleContext(final Player player) {
		String name = player.getName();
		
		if(playerTriggerContexts.get(name) == null) {
			
			playerTriggerStates.put(name, TriggerState.TRIGGER);
			playerTriggerContexts.put(name, new TriggerContext());
			
			player.sendMessage(ChatColor.YELLOW + "New Context.. BuildState: TRIGGER");
			
		} else {
			if(playerTriggerContexts.get(name).isComplete()) {
				//TODO: actually handle the Context before deleting it
				playerTriggerStates.put(name, TriggerState.IDLE);
				playerTriggerContexts.put(name, null);
				
				player.sendMessage(ChatColor.YELLOW + "Handed over trigger context.");
			} else {
				player.sendMessage(ChatColor.YELLOW + "Context not complete. Use [F12] to cancel.");
				return;
			}
		}
	}

	/**
	 * Toggles a players TriggerState unless it is idle
	 * @param player the bukkit player
	 */
	private void toggleTriggerState(final Player player) {
		String name = player.getName();
		TriggerState old = playerTriggerStates.get(name);
	
		if(old == TriggerState.TRIGGER) {
			playerTriggerStates.put(name, TriggerState.RESPONSE);
			player.sendMessage(ChatColor.YELLOW + "BuildState: RESPONSE");
			return;
		} 
		if(old == TriggerState.RESPONSE) {
			playerTriggerStates.put(name, TriggerState.STANDBY);
			player.sendMessage(ChatColor.YELLOW + "BuildState: STANDBY");
			return;
		}
		if(old == TriggerState.STANDBY) {
			playerTriggerStates.put(name, TriggerState.TRIGGER);
			player.sendMessage(ChatColor.YELLOW + "BuildState: TRIGGER");
			return;
		}
		
		player.sendMessage(ChatColor.YELLOW + "No active TriggerContext");
	}
	
	/**
	 * deregisters the current context of a player
	 * @param player bukkit player
	 */
	private void cancelContext(final Player player) {
		String name = player.getName();
		if(playerTriggerStates.get(name) == TriggerState.IDLE) {
			player.sendMessage(ChatColor.YELLOW + "No active TriggerContext");
			return;
		}
		playerTriggerStates.put(name, TriggerState.IDLE);
		playerTriggerContexts.put(name, null);
		
		player.sendMessage(ChatColor.YELLOW + "Cancelled your TriggerContext");
	}
	
	/**
	 * creates the triggerrelevant data for joining players
	 * @param event a player joins the server
	 */
	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String name = player.getName();
		
		addPlayer(name);
		
		player.performCommand("helpme");
		player.setGameMode(GameMode.CREATIVE);
	}
	
	/**
	 * removes the triggerrelevant data for quitting players
	 * @param event a player (rage)quits
	 */
	@EventHandler
	public void onPlayerLeave(final PlayerQuitEvent event) {
		String name = event.getPlayer().getName();
		
		removePlayer(name);
	}
	
	
	/**
	 * creates the triggerrelevant data for a new player
	 * @param name player name
	 */
	private void addPlayer(final String name) {
		playerTriggerStates.put(name, TriggerState.IDLE);
		playerTriggerContexts.put(name, null);
	}

	/**
	 * removes the triggerrelevant data of a player
	 * @param name player name
	 */
	private void removePlayer(final String name) {
		playerTriggerStates.remove(name);
		playerTriggerContexts.remove(name);
	}
	
	
}
