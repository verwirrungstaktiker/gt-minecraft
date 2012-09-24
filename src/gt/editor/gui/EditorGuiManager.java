/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.editor.gui;

import gt.editor.EditorFacade;

import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.gui.PopupScreen;
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
		
		
		PopupScreen popup = evt.getPlayer().getMainScreen().getActivePopup();
		
		if(evt.getPlayer().getActiveScreen() == ScreenType.GAME_SCREEN) {
			evt.getPlayer()
				.getMainScreen()
				.attachPopupScreen(new EditorOverlay(evt, facade));
			
		} else if(popup instanceof EditorOverlay && ((EditorOverlay)popup).closeWithHotkey() ) {
			evt.getPlayer().getMainScreen().closePopup();
			evt.getPlayer().openScreen(ScreenType.GAME_SCREEN);	
		}
	}
	
	
}
