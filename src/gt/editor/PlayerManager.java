/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.editor;

import static com.google.common.collect.Maps.newHashMap;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.YELLOW;
import gt.general.logic.TriggerContext;
import gt.general.logic.persistence.YamlSerializable;

import java.util.Collection;
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

/**
 * Manages Player states when they use the Editor
 * @author Roman
 *
 */
public class PlayerManager implements Listener{	

	private final Map<Player, EditorPlayer> players = newHashMap();
	
	private EditorTriggerManager triggerManager;
	private ParticleManager particleManager;
	
	/**
	 * @param triggerManager holds the TriggerContexts of the current map
	 * @param particleManager the ParticleManager (for highlighting)
	 */
	public PlayerManager(final EditorTriggerManager triggerManager, final ParticleManager particleManager) {
		this.triggerManager = triggerManager;
		this.particleManager = particleManager;
	}
	
	
	/**
	 * deregister Triggers and Responses
	 * @param event a block is broken
	 */
	@EventHandler
	public void onBlockDestroyed(final BlockBreakEvent event) {
		
		EditorPlayer player = getEditorPlayer(event);

		Block block = event.getBlock();
		
		if(triggerManager.isSerializable(block)) {

			TriggerContext context = triggerManager.getContext(block);
			YamlSerializable serializable = triggerManager.getSerializable(block);
			
			String serLabel = serializable.getLabel();
			String contextLabel = context.getLabel();
		
			
			if(player.hasActiveContext()) {
				
				if(player.isInContext(context)) {
	
					//particleManager.removeSerializable(serializable, player.getPlayer());
					triggerManager.deleteBlock(block);
	
					player.sendMessage(YELLOW + "Deleted " + serLabel + " from " + contextLabel);
				} else {				
					player.sendMessage(YELLOW + "Finish your open Context first.");
					event.setCancelled(true);
				}
			} else {
				System.out.println("Switching to context.");
				switchToContext(player, context);
				event.setCancelled(true);
			}
		}
	}

	/**
	 * switch to another active TriggerContext
	 * @param player a bukkit player
	 * @param context the new TriggerContext
	 */
	public void switchToContext(final EditorPlayer player, final TriggerContext context) {
		
		if(player.getActiveContext() != null) {
			player.exitContext();
		}

		player.enterContext(context);
		//particleManager.addContext(context, ParticleType.DRIPLAVA, player.getPlayer());
		
		player.sendMessage(GREEN + "Switched to Context " + context.getLabel() + " State: TRIGGER.");
		
	}

	/**
	 * handles key presses
	 * @param event player presses a key
	 */
	@EventHandler
	public void handleKeyPresses(final KeyPressedEvent event) {
		EditorPlayer player = getEditorPlayer(event.getPlayer());

		switch(event.getKey()) {
		case KEY_F6:
			toggleContext(player);
			break;
		case KEY_F7:
			player.toggleTriggerState();		
			break;
		case KEY_F9:
			toggleContextInputFunction(player);		
			break;
		case KEY_F12:
			cancelContext(player);
			break; 
		case KEY_F4:
			player.setSuppressHighlight(!player.isSuppressHighlight());
			break;
		default:
			break;
		}
	}


	/**
	 * Toggle context enter/leave
	 * @param player bukkit player
	 */
	private void toggleContext(final EditorPlayer player) {
		
		if(!player.hasActiveContext()) {
			createContext(player);
		} else {			
			if(player.getActiveContext().isComplete()) {
				player.exitContext();
			} else {
				player.sendMessage(YELLOW + "Context not complete. Use [F12] to cancel.");
				return;
			}
		}
	}


	/**
	 * toggle a players input function
	 * @param player bukkit player
	 */
	private void toggleContextInputFunction(final EditorPlayer player) {

		if(player.hasActiveContext()) {
			TriggerContext context = player.getActiveContext();

			context.toggleInputFunction();
			
			player.sendMessage(
					YELLOW + 
					"Trigger Input Fuction: " + 
					context.getInputFunction().toString());
		} else {
			player.sendMessage(YELLOW + "No active TriggerContext");
		}
	}


	/**
	 * creates the triggerrelevant data for joining players
	 * @param event a player joins the server
	 */
	@EventHandler
	public void onPlayerJoin(final PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		players.put(event.getPlayer(), new EditorPlayer(event.getPlayer()));
		
		player.performCommand("helpme");
		player.setGameMode(GameMode.CREATIVE);
	}
	
	/**
	 * removes the triggerrelevant data for quitting players
	 * @param event a player (rage)quits
	 */
	@EventHandler
	public void onPlayerLeave(final PlayerQuitEvent event) {
		players.remove(event.getPlayer());
	}
	
	/** 
	 * @param player the player for which the context should be created
	 * @return the created context
	 */
	public TriggerContext createContext(final EditorPlayer player) {
		
		TriggerContext context = new TriggerContext();
		triggerManager.addTriggerContext(context);

		player.enterContext(context);
		player.sendMessage(YELLOW + "New Context: " + context.getLabel() + "; BuildState: TRIGGER");
				
		return context;
	}
	
	/**
	 * deregisters the current context of a player
	 * @param player bukkit player
	 */
	public void cancelContext(final EditorPlayer player) {
		TriggerContext context = player.getActiveContext();
		
		if(context == null) {
			player.sendMessage(YELLOW + "No active TriggerContext");
			
		} else {
			player.exitContext();
			triggerManager.deleteTriggerContext(context);
			player.sendMessage(YELLOW + "Cancelled your TriggerContext");
		}		
	}
	
	/**
	 * @return the players
	 */
	public Collection<EditorPlayer> getEditorPlayers() {
		return players.values();
	}

	/**
	 * @param player a bukkit player
	 * @return the EditorPlayer that corresponds to the bukkit player
	 */
	public EditorPlayer getEditorPlayer (final Player player) {
		return players.get(player);
	}
	
	/**
	 * @param event event: a Block is destroyed
	 * @return the corresponding EditorPlayer who broke the block
	 */
	public EditorPlayer getEditorPlayer (final BlockBreakEvent event) {
		return players.get(event.getPlayer());
	}


	/**
	 * @return the triggerManager
	 */
	public EditorTriggerManager getTriggerManager() {
		return triggerManager;
	}


	/**
	 * @return the particleManager
	 */
	public ParticleManager getParticleManager() {
		return particleManager;
	}
}
