package gt.editor.gui;

import static com.google.common.collect.Maps.*;
import gt.editor.EditorTriggerManager;
import gt.plugin.meta.Hello;

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
		System.out.println("ding");
		System.out.println(evt.getScreenType());
		
		boolean overlayOpen = openOverlays.containsKey(evt.getPlayer());
		
		if(evt.getPlayer().getActiveScreen() == ScreenType.GAME_SCREEN || overlayOpen) {
			
			if(! overlayOpen) {
				TriggerOverlay triggerOverlay = new TriggerOverlay(evt.getPlayer(), triggerManager);
				
				evt.getPlayer()
					.getMainScreen()
					.attachPopupScreen(triggerOverlay);
				
			} else {
				evt.getPlayer().getMainScreen().closePopup();
				evt.getPlayer().openScreen(ScreenType.GAME_SCREEN);
				
			}
		}
	}
	
	
}
