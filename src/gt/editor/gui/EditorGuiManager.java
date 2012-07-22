package gt.editor.gui;

import gt.editor.EditorTriggerManager;
import gt.editor.PlayerManager;

import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.keyboard.BindingExecutionDelegate;

public class EditorGuiManager implements BindingExecutionDelegate {

	private EditorTriggerManager triggerManager;
	private PlayerManager playerManager;

	public EditorGuiManager(final EditorTriggerManager triggerManager, final PlayerManager playerManager) {
		this.triggerManager = triggerManager;
		this.playerManager = playerManager;
	}
	
		
	@Override
	public void keyReleased(final KeyBindingEvent evt) {}
	
	@Override
	public void keyPressed(final KeyBindingEvent evt) {
		
		
		boolean triggerOverlayOpen = evt.getPlayer().getMainScreen().getActivePopup() instanceof TriggerOverlay;
		
		if(evt.getPlayer().getActiveScreen() == ScreenType.GAME_SCREEN) {
			evt.getPlayer()
				.getMainScreen()
				.attachPopupScreen(new TriggerOverlay(evt.getPlayer(), triggerManager, playerManager));
			
		} else if(triggerOverlayOpen) {
			evt.getPlayer().getMainScreen().closePopup();
			evt.getPlayer().openScreen(ScreenType.GAME_SCREEN);	
		}
	}
	
	
}
