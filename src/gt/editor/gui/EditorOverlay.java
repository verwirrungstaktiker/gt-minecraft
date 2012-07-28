package gt.editor.gui;

import gt.editor.EditorFacade;
import gt.plugin.meta.MultiListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.getspout.spoutapi.event.screen.ScreenCloseEvent;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

public class EditorOverlay extends GenericPopup implements Listener {
	
	private final SpoutPlayer player;
	private final EditorFacade facade;

	private OverlayPage activePage;
	

	/**
	 * 
	 * @param player the player who opened this overlay
	 * @param facade facade of the editor
	 */
	public EditorOverlay(final SpoutPlayer player, final EditorFacade facade) {
		this.player = player;
		this.facade = facade;
		
		switchToPage(getMainPage());
	}

	
	
	public void switchToPage(final OverlayPage page) {
		
		if(activePage != null) {
			activePage.hide();
		}
		
		activePage = page;
		page.show();
	}
	
	
	public MainPage getMainPage () {
		return new MainPage(this, player, facade);
	}
	
	public PromptPage getPromptPage(final String message, final PromptCallback callback) {
		return new PromptPage(this, player, facade, message, callback);
	}
	
	
	@EventHandler
	public void onClose(final ScreenCloseEvent event){
		if(event.getScreen() == this) {
			super.onScreenClose(event);
			
			MultiListener.unregisterListener(this);
		}
	}

	public boolean closeWithHotkey() {
		
		if(activePage != null) {
			return activePage.closeWithHotkey();
		} else {
			return false;
		}
	}	
}
