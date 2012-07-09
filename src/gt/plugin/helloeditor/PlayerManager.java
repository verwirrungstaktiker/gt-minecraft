package gt.plugin.helloeditor;

import static org.bukkit.ChatColor.*;
import gt.general.logic.TriggerContext;
import gt.general.logic.persistance.YamlSerializable;
import gt.general.logic.response.Response;
import gt.general.logic.trigger.Trigger;
import gt.plugin.meta.Hello;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.particle.Particle.ParticleType;

public class PlayerManager implements Listener{
	
	public enum TriggerState {
		IDLE,		// no triggercontext
		TRIGGER,
		RESPONSE,
		STANDBY		// triggercontext but standby
	}
	
	/** contains all player's current TriggerStates **/
	private Map<String, TriggerState> playerTriggerStates = new HashMap<String, TriggerState>();
	/** contains all player's current TriggerContext's **/
	private Map<String, TriggerContext> playerTriggerContexts = new HashMap<String, TriggerContext>();

	private EditorTriggerManager triggerManager;
	
	private ParticleManager particleManager;
	
	/**
	 * @param triggerManager holds the TriggerContexts of the current map
	 */
	public PlayerManager(final EditorTriggerManager triggerManager) {
		this.triggerManager = triggerManager;
		this.particleManager = new ParticleManager();
		Hello.scheduleSyncTask(particleManager, 0, 20);
	}
	
	
	/**
	 * deregister Triggers and Responses
	 * @param event a block is broken
	 */
	@EventHandler
	public void onBlockDestroyed(final BlockBreakEvent event) {
		
		Player player = event.getPlayer();
		String name = event.getPlayer().getName();
		Block block = event.getBlock();
		
		if(triggerManager.isSerializable(block)) {

			TriggerContext context = triggerManager.getContext(block);
			YamlSerializable serializable = triggerManager.getSerializable(block);
			
			String serLabel = serializable.getLabel();
			String contextLabel = context.getLabel();
		
			switch(getState(name)) {
				case TRIGGER: 
				case RESPONSE:
				case STANDBY:
					if(playerTriggerContexts.get(name) == context) {
						System.out.println("Breaking serializable block.");
						//kill block highlight
						particleManager.removeSerializable(serializable, player);
						//handle block break					
						triggerManager.deleteBlock(block);

						player.sendMessage(YELLOW + "Deleted " + serLabel + " from " + contextLabel);
					} else {
						System.out.println("Other unfinished context.");
						//other unfinished context
						player.sendMessage(YELLOW + "Finish your open Context first.");
						event.setCancelled(true);
					}
					break;
				case IDLE:
					System.out.println("Switching to context.");
					//goto corresponding context
					playerTriggerContexts.put(name, context);
					playerTriggerStates.put(name, TriggerState.TRIGGER);
					//add highlight for whole context
					highlightContext(player);
					
					player.sendMessage(GREEN + "Switched to Context " + contextLabel+ " State: TRIGGER.");
					event.setCancelled(true);
					break;
				default:
			}
		}
	}
	
	public void addTrigger(Trigger trigger, TriggerContext context, Player player) {
		triggerManager.addTrigger(trigger, context);
		particleManager.addSerializable(trigger, ParticleType.DRIPLAVA, player);
	}
	
	public void addResponse(Response response, TriggerContext context, Player player) {
		triggerManager.addResponse(response, context);
		particleManager.addSerializable(response, ParticleType.DRIPLAVA, player);
	}

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
		case KEY_F4:
			highlightContext(player);
			break;
		default:
			break;
		}
	}
	
	/**
	 * highlight the active context of a player
	 * @param player a bukkit player
	 */
	private void highlightContext(final Player player) {
		TriggerContext context = playerTriggerContexts.get(player.getName());
		if(context!=null) {
			particleManager.addContext(context, ParticleType.DRIPLAVA, player);
		}
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
		TriggerContext context = playerTriggerContexts.get(name);
		
		if(context == null) {
			
			playerTriggerStates.put(name, TriggerState.TRIGGER);
			
			context = new TriggerContext();
			triggerManager.addTriggerContext(context);
			playerTriggerContexts.put(name, context);
			
			player.sendMessage(YELLOW + "New Context: " + context.getLabel() + "; BuildState: TRIGGER");
			
		} else {
			if(context.isComplete()) {
				// TODO actually handle the Context before deleting it
				playerTriggerStates.put(name, TriggerState.IDLE);
				playerTriggerContexts.put(name, null);
				
				particleManager.removeContext(context, player);
				
				player.sendMessage(YELLOW + "Handed over " + context.getLabel() + ".");
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
	
		switch (playerTriggerStates.get(name)) {
		case TRIGGER:
			playerTriggerStates.put(name, TriggerState.RESPONSE);
			player.sendMessage(YELLOW + "BuildState: RESPONSE");
			return;
			
		case RESPONSE:
			playerTriggerStates.put(name, TriggerState.STANDBY);
			player.sendMessage(YELLOW + "BuildState: STANDBY");
			return;
			
		case STANDBY:
			playerTriggerStates.put(name, TriggerState.TRIGGER);
			player.sendMessage(YELLOW + "BuildState: TRIGGER");
			return;
			
		default:
			player.sendMessage(YELLOW + "No active TriggerContext");
			break;
		}		
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
		triggerManager.deleteTriggerContext(context);
		
		
		player.sendMessage(YELLOW + "Cancelled your TriggerContext");
	}
	
	/**
	 * @param name the player whose context should be returned
	 * @return the context
	 */
	public TriggerContext getContext(final String name) {
		return playerTriggerContexts.get(name);
	}
	
	/**
	 * @param name the player whose state should be returned
	 * @return the state
	 */
	public TriggerState getState(final String name) {
		return playerTriggerStates.get(name);
	}
	
}
