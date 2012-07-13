package gt.editor.gui;

import static com.google.common.collect.Maps.*;
import gt.editor.EditorTriggerManager;

import java.util.Map;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.keyboard.BindingExecutionDelegate;

public class GuiManager implements BindingExecutionDelegate {

	private EditorTriggerManager triggerManager;

	private Map<Player, TriggerOverlay> openOverlays = newHashMap();
	
	
	public GuiManager(final EditorTriggerManager triggerManager) {
		this.triggerManager = triggerManager;
	}
	
		
	@Override
	public void keyReleased(final KeyBindingEvent evt) {}
	
	@Override
	public void keyPressed(final KeyBindingEvent evt) {
		
		
		boolean triggerOverlayOpen = evt.getPlayer().getMainScreen().getActivePopup() instanceof TriggerOverlay;
		
		if(evt.getPlayer().getActiveScreen() == ScreenType.GAME_SCREEN) {
			evt.getPlayer()
				.getMainScreen()
				.attachPopupScreen(new TriggerOverlay(evt.getPlayer(), triggerManager));
			
		} else if(triggerOverlayOpen) {
			evt.getPlayer().getMainScreen().closePopup();
			evt.getPlayer().openScreen(ScreenType.GAME_SCREEN);	
		}
	}
	
	
}
