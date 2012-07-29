package gt.editor;

import gt.editor.EditorPlayer.TriggerState;
import gt.general.logic.TriggerContext;
import gt.general.logic.persistance.YamlSerializable;

import java.util.Collection;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

public class EditorFacade {

	private final PlayerManager playerManager;
	private final EditorTriggerManager triggerManager;
	private final ParticleManager particleManager;

	public EditorFacade(final EditorTriggerManager triggerManager, final PlayerManager playerManager, final ParticleManager particleManager) {
		this.triggerManager = triggerManager;
		this.playerManager = playerManager;
		this.particleManager = particleManager;
	}
	
	/*
	 * state of system
	 */
	public TriggerContext getActiveContext(final Player player) {
		return getEditorPlayer(player).getActiveContext();
	}
	
	public YamlSerializable getSelectedItem(final Player player) {
		return getEditorPlayer(player).getSelectedItem();
	}
	
	public void setSelectedItem(final Player player, final YamlSerializable selectedItem) {
		getEditorPlayer(player).setSelectedItem(selectedItem);
	}
	
	public Collection<TriggerContext> getAllTriggerContexts() {
		return triggerManager.getTriggerContexts();
	}
	
	/*
	 * manipulate contexts
	 */
	public void createContext(final Player player) {
		playerManager.createContext(getEditorPlayer(player));
	}
	
	public void deleteContext(final Player player) {
		playerManager.cancelContext(getEditorPlayer(player));
	}

	
	public void enterContext(final Player player, final TriggerContext context) {
		getEditorPlayer(player).enterContext(context);
	}
	
	public void exitContext(SpoutPlayer player) {
		getEditorPlayer(player).exitContext();
	}
	
	public boolean playerCanCreateContext(final Player player) {
		
		TriggerContext c = getEditorPlayer(player).getActiveContext();
		return c == null || c.isComplete();
	}
	
	
	public EditorPlayer getEditorPlayer(final Player player) {
		return playerManager.getEditorPlayer(player);
	}

	/*
	 * state of highlight
	 */
	
	public void setSuppressHighlight(final Player player, boolean supress) {
		getEditorPlayer(player).setSuppressHighlight(supress);
	}
	
	public boolean isSuppressHighlight(final Player player) {
		return getEditorPlayer(player).isSuppressHighlight();
	}
	
	public TriggerState getTriggerState(final Player player) {
		return getEditorPlayer(player).getTriggerState();
	}
	
	public void setTriggerState(final Player player, final TriggerState triggerState) {
		getEditorPlayer(player).setTriggerState(triggerState);
	}
	
	public Map<TriggerState, ItemStack[]> getInventories(final Player player) {
		return getEditorPlayer(player).getInventories();
	}
	
}
