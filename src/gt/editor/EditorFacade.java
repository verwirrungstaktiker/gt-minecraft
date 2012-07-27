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
	public TriggerContext getTriggerContext(final Player player) {
		return playerManager.getContext(player);
	}
	
	public Collection<TriggerContext> getAllTriggerContexts() {
		return triggerManager.getTriggerContexts();
	}
	
	/*
	 * manipulate contexts
	 */
	public void createContext(final Player player) {
		playerManager.createContext(player);
	}
	
	public void deleteContext(final Player player) {
		playerManager.cancelContext(player);
	}
	
	/*
	 * state of user
	 */
	
	public void switchToContext(final Player player, final TriggerContext context) {
		playerManager.switchToContext(player, context);
	}
	
	public void exitContext(SpoutPlayer player) {
		playerManager.exitContext(player);
	}
	
	public boolean playerCanCreateContext(final Player player) {
		return playerManager.canCreateContext(player);
	}
	
	/*
	 *  highlight
	 */
	
	public void setSuppressedHighlight(final Player player, final boolean supressed) {
		particleManager.setSuppressedHighlight(player, supressed);
	}
	
	public boolean hasSuppressedHighlight(final Player player) {
		return particleManager.hasSuppressedHighlight(player);
	}


	
}
