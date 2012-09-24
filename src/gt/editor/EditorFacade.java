/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.editor;

import gt.editor.EditorPlayer.TriggerState;
import gt.general.logic.TriggerContext;
import gt.general.logic.persistence.YamlSerializable;

import java.util.Collection;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

public class EditorFacade {

	private final PlayerManager playerManager;
	private final EditorTriggerManager triggerManager;

	/**
	 * Construct a new EditorFacade 
	 * @param triggerManager Manager of TriggerContexts
	 * @param playerManager Manager of building new Logic
	 */
	public EditorFacade(final EditorTriggerManager triggerManager, final PlayerManager playerManager) {
		this.triggerManager = triggerManager;
		this.playerManager = playerManager;
	}
	
	/**
	 * @param player a bukkit player
	 * @return the active TriggerContext of the player
	 */
	public TriggerContext getActiveContext(final Player player) {
		return getEditorPlayer(player).getActiveContext();
	}
	
	/**
	 * @param player a bukkit player
	 * @return the Trigger/Response that the player has selected
	 */
	public YamlSerializable getSelectedItem(final Player player) {
		return getEditorPlayer(player).getSelectedItem();
	}
	
	/**
	 * sets the selected Trigger/Response
	 * @param player a bukkit player
	 * @param selectedItem the selected Trigger/Response
	 */
	public void setSelectedItem(final Player player, final YamlSerializable selectedItem) {
		getEditorPlayer(player).setSelectedItem(selectedItem);
	}
	
	/**
	 * @return all currently working TriggerContexts
	 */
	public Collection<TriggerContext> getAllTriggerContexts() {
		return triggerManager.getTriggerContexts();
	}
	
	/*
	 * manipulate contexts
	 */
	
	/**
	 * add an active TriggerContext for a player
	 * @param player a bukkit player
	 */
	public void createContext(final Player player) {
		playerManager.createContext(getEditorPlayer(player));
	}
	
	/**
	 * delete an active TriggerContext for a player
	 * @param player a bukkit player
	 */
	public void deleteContext(final Player player) {
		playerManager.cancelContext(getEditorPlayer(player));
	}

	/**
	 * set the active TriggerContext for a player
	 * @param player a bukkit player
	 * @param context the Triggercontext
	 */
	public void enterContext(final Player player, final TriggerContext context) {
		getEditorPlayer(player).enterContext(context);
	}
	
	/**
	 * remove the active TriggerContext for a player
	 * @param player a bukkit player
	 */
	public void exitContext(final SpoutPlayer player) {
		getEditorPlayer(player).exitContext();
	}
	
	/**
	 * @param player a bukkit player
	 * @return true if the player is allowed to create a new TriggerContext
	 */
	public boolean playerCanCreateContext(final Player player) {
		
		TriggerContext c = getEditorPlayer(player).getActiveContext();
		return c == null || c.isComplete();
	}
	
	/**
	 * @param player a bukkit player
	 * @return the EditorPlayer
	 */
	public EditorPlayer getEditorPlayer(final Player player) {
		return playerManager.getEditorPlayer(player);
	}

	/*
	 * state of highlight
	 */
	
	/**
	 * set the suppressing of block highlights for a player
	 * @param player a bukkit player
	 * @param suppress suppressed if true
	 */
	public void setSuppressHighlight(final Player player, final boolean suppress) {
		getEditorPlayer(player).setSuppressHighlight(suppress);
	}
	
	/**
	 * @param player a bukkit player
	 * @return true if highlight is suppressed for the player
	 */
	public boolean isSuppressHighlight(final Player player) {
		return getEditorPlayer(player).isSuppressHighlight();
	}
	
	/**
	 * @param player a bukkit player
	 * @return the TriggerState of the player (Trigger/Response/Standbye)
	 */
	public TriggerState getTriggerState(final Player player) {
		return getEditorPlayer(player).getTriggerState();
	}
	
	/**
	 * set the TriggerState for a player (Trigger/Response/Standbye)
	 * @param player a bukkit player
	 * @param triggerState the new TriggerState
	 */
	public void setTriggerState(final Player player, final TriggerState triggerState) {
		getEditorPlayer(player).setTriggerState(triggerState);
	}
	
	/**
	 * @param player a bukkit player
	 * @return the Inventories for all TriggerStates of a player
	 */
	public Map<TriggerState, ItemStack[]> getInventories(final Player player) {
		return getEditorPlayer(player).getInventories();
	}
	
}
