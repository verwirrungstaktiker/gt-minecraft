package gt.plugin.helloeditor;

import gt.general.trigger.PressurePlateTrigger;
import gt.general.trigger.TriggerContext;
import gt.general.trigger.response.DoorResponse;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.getspout.spoutapi.event.input.KeyPressedEvent;


public class BuildManager implements Listener {

	private static final ChatColor YELLOW = ChatColor.YELLOW;
	private static final ChatColor RED = ChatColor.RED;
	private static final ChatColor GREEN = ChatColor.GREEN;
	
	public enum TriggerState {
		IDLE,		// no triggercontext
		TRIGGER,
		RESPONSE,
		STANDBY		// triggercontext but standbye
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

		switch(event.getKey()) {
		case KEY_F6:
			toggleContext(player);
			break;
		case KEY_F7:
			toggleTriggerState(player);		
			break;
		case KEY_F9:
			toggleContextInputFunction(player);		
			break;
		case KEY_F12:
			cancelContext(player);
			break; 
		default:
			break;
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
		if(playerTriggerStates.get(name) == TriggerState.TRIGGER) {
			if(isUsableAsTrigger(block)) {
				addTrigger(name, block);
				player.sendMessage(GREEN + "Trigger has been added");
			} else {
				player.sendMessage(RED + "This Block can't be used as Trigger.");
				event.setCancelled(true);
			}
		}
		// Response
		if(playerTriggerStates.get(name) == TriggerState.RESPONSE) {
			if(isUsableAsResponse(block)) {
				addResponse(name, block);
			} else {
				player.sendMessage(RED + "This Block can't be used as Response.");
				event.setCancelled(true);
			}
		}
	}

	/**
	 * @param block
	 * @return true if the block is usable as trigger
	 */
	private boolean isUsableAsTrigger(Block block) {
		// TODO
		return block.getType() == Material.STONE_PLATE  || block.getType() == Material.WOOD_PLATE;
	}
	
	/**
	 * @param block
	 * @return true if the block is usable as response
	 */
	private boolean isUsableAsResponse(Block block) {
		// TODO
		return block.getType() == Material.WOOD_DOOR || block.getType() == Material.IRON_DOOR_BLOCK ||
			 block.getType() == Material.WOODEN_DOOR || block.getType() == Material.IRON_DOOR;
	}
	
	private void addTrigger(String name, Block block) {
		switch(block.getType()) {
			case WOOD_PLATE:
			case STONE_PLATE:
				PressurePlateTrigger trigger = new PressurePlateTrigger(block);
				playerTriggerContexts.get(name).getTriggers().add(trigger);
				
				break;
			default: 
				System.out.println("This can't happen. Check Usable Trigger Blocks.");
		}
		
	}

	private void addResponse(String name, Block block) {
		switch(block.getType()) {
			case WOOD_DOOR:
			case WOODEN_DOOR:
			case IRON_DOOR:
			case IRON_DOOR_BLOCK: 
				DoorResponse response = new DoorResponse(block);
				playerTriggerContexts.get(name).getResponses().add(response);
				break;
			default:
				System.out.println("This can't happen. Check Usable Response Blocks.");
		}
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
					YELLOW + 
					"Trigger Input Fuction: " + 
					playerTriggerContexts.get(name).getInputFunction().toString());
		} else {
			player.sendMessage(YELLOW + "No active TriggerContext");
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
			
			TriggerContext context = new TriggerContext();
			HelloEditor.getPlugin().getTm().addTriggerContext(context);
			playerTriggerContexts.put(name, context);
			
			player.sendMessage(YELLOW + "New Context.. BuildState: TRIGGER");
			
		} else {
			if(playerTriggerContexts.get(name).isComplete()) {
				//TODO: actually handle the Context before deleting it
				playerTriggerStates.put(name, TriggerState.IDLE);
				playerTriggerContexts.put(name, null);
				
				player.sendMessage(YELLOW + "Handed over trigger context.");
			} else {
				player.sendMessage(YELLOW + "Context not complete. Use [F12] to cancel.");
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
			player.sendMessage(YELLOW + "BuildState: RESPONSE");
			return;
		} 
		if(old == TriggerState.RESPONSE) {
			playerTriggerStates.put(name, TriggerState.STANDBY);
			player.sendMessage(YELLOW + "BuildState: STANDBY");
			return;
		}
		if(old == TriggerState.STANDBY) {
			playerTriggerStates.put(name, TriggerState.TRIGGER);
			player.sendMessage(YELLOW + "BuildState: TRIGGER");
			return;
		}
		
		player.sendMessage(YELLOW + "No active TriggerContext");
	}
	
	/**
	 * deregisters the current context of a player
	 * @param player bukkit player
	 */
	private void cancelContext(final Player player) {
		String name = player.getName();
		if(playerTriggerStates.get(name) == TriggerState.IDLE) {
			player.sendMessage(YELLOW + "No active TriggerContext");
			return;
		}
		playerTriggerStates.put(name, TriggerState.IDLE);
		TriggerContext context = playerTriggerContexts.get(name);
		playerTriggerContexts.put(name, null);
		HelloEditor.getPlugin().getTm().deleteTriggerContext(context);
		
		
		player.sendMessage(YELLOW + "Cancelled your TriggerContext");
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