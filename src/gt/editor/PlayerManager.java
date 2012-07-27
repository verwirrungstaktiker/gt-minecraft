package gt.editor;

import static com.google.common.collect.Maps.*;
import static org.bukkit.ChatColor.*;
import gt.editor.EditorPlayer.TriggerState;
import gt.general.logic.TriggerContext;
import gt.general.logic.persistance.YamlSerializable;

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
import org.getspout.spoutapi.particle.Particle.ParticleType;

public class PlayerManager implements Listener{	

	private final Map<Player, EditorPlayer> players = newHashMap();
	
	private EditorTriggerManager triggerManager;
	private ParticleManager particleManager;
	
	/**
	 * @param triggerManager holds the TriggerContexts of the current map
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
		
			switch(player.getTriggerState()) {
				case TRIGGER: 
				case RESPONSE:
				case STANDBY:
					if(player.isInContext(context)) {

						//particleManager.removeSerializable(serializable, player.getPlayer());
						triggerManager.deleteBlock(block);

						player.sendMessage(YELLOW + "Deleted " + serLabel + " from " + contextLabel);
					} else {
						player.sendMessage(YELLOW + "Finish your open Context first.");
						event.setCancelled(true);
					}
					break;
					
				case IDLE:
					System.out.println("Switching to context.");
					switchToContext(player, context);
					event.setCancelled(true);
					break;
				default:
			}
		}
	}


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
		
		if(player.getTriggerState() == TriggerState.IDLE) {
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


	public EditorPlayer getEditorPlayer (final Player player) {
		return players.get(player);
	}
	
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


///** player inventories are saved here when they build TriggerContexts **/
//private Map<String, ItemStack[]> playerInventories = newHashMap();
//	
//private static final ItemStack[] TRIGGER_BLOCKS = new ItemStack[]{
//		new ItemStack(Material.LEVER),
//		new ItemStack(Material.STONE_BUTTON),
//		new ItemStack(Material.WOOD_PLATE),
//		new ItemStack(Material.STONE_PLATE),
//		CustomBlockType.GNOME_TRIGGER_NEGATIVE.getItemStack()
//};
//
//private static final ItemStack[] RESPONSE_BLOCKS = new ItemStack[]{
//		new ItemStack(Material.WOOD_DOOR),
//		new ItemStack(Material.IRON_DOOR),
//		new ItemStack(Material.DIAMOND_BLOCK),
//		new ItemStack(Material.REDSTONE_TORCH_ON),
//		new ItemStack(Material.SIGN)
//};

///**
//* change the triggerState for a player
//* @param player bukkit player
//* @param state the new triggerState
//*/
//private void changeTriggerState(final Player player, final TriggerState state) {
//	Inventory inv = player.getInventory();
//	TriggerState oldState = playerTriggerStates.get(player.getName());
//	
//	if(oldState == TriggerState.STANDBY || oldState == TriggerState.IDLE) {
//		saveInventory(player);
//	}
//	
//	switch (state) {
//	case TRIGGER:
//		inv.setContents(TRIGGER_BLOCKS);
//		break;
//
//	case RESPONSE:
//		inv.setContents(RESPONSE_BLOCKS);
//		break;
//
//	case STANDBY:
//		loadInventory(player);
//		break;
//	default:
//		break;
//	}
//	
//	playerTriggerStates.put(player.getName(), state);
//	player.sendMessage(YELLOW + "" +  state + " :");
//}
//
///**
//* Toggles a players TriggerState unless it is idle
//* @param player the bukkit player
//*/
//private void toggleTriggerState(final Player player) {
//	String name = player.getName();
//
//	switch (playerTriggerStates.get(name)) {
//	case TRIGGER:
//		changeTriggerState(player, TriggerState.RESPONSE);
//		return;
//		
//	case RESPONSE:
//		changeTriggerState(player, TriggerState.STANDBY);
//		
//		loadInventory(player);
//		return;
//		
//	case STANDBY:
//		changeTriggerState(player, TriggerState.TRIGGER);
//		return;
//		
//	default:
//		player.sendMessage(YELLOW + "No active TriggerContext.");
//		break;
//	}		
//}
//
///**
//* save the players inventory
//* @param player bukkit player
//*/
//private void saveInventory(final Player player) {
//	playerInventories.put(player.getName(), player.getInventory().getContents());
//}
//
///**
//* load the players old inventory and set it
//* @param player bukkit player
//*/
//private void loadInventory(final Player player) {
//	ItemStack[] items = playerInventories.remove(player.getName());
//	if(items!=null) {
//		player.getInventory().setContents(items);
//	}
//}

