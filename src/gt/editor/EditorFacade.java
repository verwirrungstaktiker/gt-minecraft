package gt.editor;

import gt.general.logic.TriggerContext;

import java.util.Collection;

import org.bukkit.entity.Player;
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
	
}
