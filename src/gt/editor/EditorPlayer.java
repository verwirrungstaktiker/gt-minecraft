/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.editor;

import static com.google.common.collect.Maps.newEnumMap;
import static org.bukkit.ChatColor.GREEN;
import static org.bukkit.ChatColor.RED;
import static org.bukkit.ChatColor.YELLOW;
import gt.editor.event.HighlightSuppressEvent;
import gt.editor.event.LogicSelectionEvent;
import gt.general.logic.TriggerContext;
import gt.general.logic.persistence.YamlSerializable;
import gt.plugin.meta.CustomBlockType;
import gt.plugin.meta.Hello;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EditorPlayer {

	private static final TriggerContext NO_CONTEXT = null;

	private static final ItemStack[] TRIGGER_BLOCKS = new ItemStack[]{
		new ItemStack(Material.LEVER),
		new ItemStack(Material.STONE_BUTTON),
		new ItemStack(Material.WOOD_PLATE),
		new ItemStack(Material.STONE_PLATE),
		CustomBlockType.GNOME_TRIGGER_NEGATIVE.getItemStack(),
		CustomBlockType.GNOME_STORAGE_NEGATIVE.getItemStack(),
		CustomBlockType.STEP_ON_TRIGGER.getItemStack(),
		CustomBlockType.QUESTION_BLOCK.getItemStack(),
		CustomBlockType.BLUE_LOCK.getItemStack()
	};
	
	private static final ItemStack[] RESPONSE_BLOCKS = new ItemStack[]{
		new ItemStack(Material.WOOD_DOOR),
		new ItemStack(Material.IRON_DOOR),
		new ItemStack(Material.DIAMOND_BLOCK),
		new ItemStack(Material.REDSTONE_TORCH_ON),
		new ItemStack(Material.SIGN),
		CustomBlockType.ZOMBIESPAWN_BLOCK.getItemStack(),
		CustomBlockType.TELEPORT_EXIT.getItemStack()
	};
	
	public enum TriggerState {
		TRIGGER,
		RESPONSE,
		STANDBY		// triggercontext but standby
	}
	
	private final Map<TriggerState, ItemStack[]> inventories = newEnumMap(TriggerState.class);
	
	private final Player player;

	private TriggerState triggerState;
	private TriggerContext activeContext;
	
	private YamlSerializable selectedItem;
	
	private boolean suppressHighlight;
	
	/**
	 * Construct a new EditorPlayer
	 * @param player a bukkit player
	 */
	public EditorPlayer (final Player player) {
		this.player = player;
		
		triggerState = TriggerState.STANDBY;
		activeContext = NO_CONTEXT;
		
		inventories.put(TriggerState.TRIGGER, TRIGGER_BLOCKS);
		inventories.put(TriggerState.RESPONSE, RESPONSE_BLOCKS);
		inventories.put(TriggerState.STANDBY, player.getInventory().getContents());
		
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

		if(!hasActiveContext() && (triggerState == TriggerState.TRIGGER ||
									triggerState == TriggerState.RESPONSE)) {
			
			sendMessage(RED + "Cannot equip that Block Set - Not in a Context!");
			return;
		}
		
		if(this.triggerState == TriggerState.STANDBY ) {
			inventories.put(TriggerState.STANDBY, player.getInventory().getContents());
		}
	
		this.triggerState = triggerState;
		player.getInventory().setContents(inventories.get(triggerState));
		
		sendMessage(GREEN + "Entered TriggerState: " + triggerState);
	}	
	
	/**
	 * Switch through the different TriggerStates
	 */
	public void toggleTriggerState() {
	
		switch (triggerState) {
		case TRIGGER:
			setTriggerState(TriggerState.RESPONSE);
			return;
			
		case RESPONSE:
			setTriggerState(TriggerState.STANDBY);
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
	 * @param context the active TriggerContext to set
	 */
	public void enterContext(final TriggerContext context) {
		activeContext = context;
		
		Hello.callEvent(new LogicSelectionEvent(player));
	}
	
	/**
	 * Leave the active TriggerContext
	 */
	public void exitContext() {		
		
		// if we are in a triggerstate, that makes only sense in a context -> leave it
		if(triggerState == TriggerState.TRIGGER || triggerState == TriggerState.RESPONSE) {
			setTriggerState(TriggerState.STANDBY);
		}
		
		
		activeContext = NO_CONTEXT;
		
		Hello.callEvent(new LogicSelectionEvent(player));
	}
	
	/**
	 * @param other a TriggerContext
	 * @return Check if a TriggerContext is the active TriggerContext of that player
	 */
	public boolean isInContext(final TriggerContext other) {
		return activeContext == other;
	}
	
	/**
	 * @return true if the player has no active TriggerContext
	 */
	public boolean hasActiveContext() {
		return activeContext != NO_CONTEXT;
	}
	
	/**
	 * send a message to the player
	 * @param msg the message
	 */
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
	public void setSelectedItem(final YamlSerializable selectedItem) {
		this.selectedItem = selectedItem;
		Hello.callEvent(new LogicSelectionEvent(player));
	}

	/**
	 * @return the corresponding Inventories for all TriggerStates
	 */
	public Map<TriggerState, ItemStack[]> getInventories() {
		
		if(triggerState == TriggerState.STANDBY) {
			inventories.put(TriggerState.STANDBY, player.getInventory().getContents());
		}
		
		return inventories;
	}
}
