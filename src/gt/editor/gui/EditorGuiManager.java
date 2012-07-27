package gt.editor.gui;

import gt.editor.EditorFacade;
import gt.editor.EditorTriggerManager;
import gt.editor.PlayerManager;

import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.keyboard.BindingExecutionDelegate;

public class EditorGuiManager implements BindingExecutionDelegate {
	
	private final EditorFacade facade;
		
	public EditorGuiManager(EditorFacade facade) {
		this.facade = facade;
	}


	@Override
	public void keyReleased(final KeyBindingEvent evt) {}
	
	@Override
	public void keyPressed(final KeyBindingEvent evt) {
		
		
		boolean triggerOverlayOpen = evt.getPlayer().getMainScreen().getActivePopup() instanceof TriggerOverlay;
		
		if(evt.getPlayer().getActiveScreen() == ScreenType.GAME_SCREEN) {
			evt.getPlayer()
				.getMainScreen()
				.attachPopupScreen(new TriggerOverlay(evt.getPlayer(), facade));
			
		} else if(triggerOverlayOpen) {
			evt.getPlayer().getMainScreen().closePopup();
			evt.getPlayer().openScreen(ScreenType.GAME_SCREEN);	
		}
	}
	
	
}
