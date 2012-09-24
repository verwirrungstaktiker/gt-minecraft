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
import gt.plugin.meta.MultiListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.event.screen.ScreenCloseEvent;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.player.SpoutPlayer;

public class EditorOverlay extends GenericPopup implements Listener {
	
	private final SpoutPlayer player;
	private final EditorFacade facade;

	private OverlayPage activePage;
	
	public enum LandingPage {
		TRIGGER_PAGE, // aka main page
		BLOCKS_PAGE
	}
	

	/**
	 * 
	 * @param evt event: Binding of a KeyShortcut
	 * @param facade facade of the editor
	 */
	public EditorOverlay(final KeyBindingEvent evt, final EditorFacade facade) {
		this.player = evt.getPlayer();
		this.facade = facade;
		
		setWidth(100);
		setHeight(100);
		
		switch (LandingPage.valueOf(evt.getBinding().getId())) {
		case TRIGGER_PAGE:
			System.out.println("launching trigger page");
			switchToPage(getMainPage());
			break;

		case BLOCKS_PAGE:
			System.out.println("launching blocks page");
			switchToPage(getBlocksPage());
			break;
			
		default:
			System.out.println("unknown page");
			break;
		}
		
		
	}

	/**
	 * Switch to another Gui Overlay Element
	 * @param page the new Overlay Page
	 */
	public void switchToPage(final OverlayPage page) {
		
		if(activePage != null) {
			activePage.hide();
		}
		
		activePage = page;
		page.show();
	}
	
	/**
	 * @return the Main Overlay Page
	 */
	public MainPage getMainPage () {
		return new MainPage(this, player, facade);
	}
	
	/**
	 * @return the Block Overlay Page
	 */
	public BlocksPage getBlocksPage() {
		return new BlocksPage(this, player, facade);
	}
	
	/** 
	 * @param message Questions of the Prompt
	 * @param callback Callback of the Prompt
	 * @return a Prompt Overlay Page
	 */
	public PromptPage getPromptPage(final String message, final PromptCallback callback) {
		return new PromptPage(this, player, facade, message, callback);
	}
	
	/**
	 * is executed on Overlay closing
	 * cleanup after closing
	 * @param event event: closing of an Overlay
	 */
	@EventHandler
	public void onClose(final ScreenCloseEvent event){
		if(event.getScreen() == this) {
			super.onScreenClose(event);
			
			MultiListener.unregisterListener(this);
		}
	}

	/**
	 * @return true if Page can be closed with a Hotkey
	 */
	public boolean closeWithHotkey() {
		
		if(activePage != null) {
			return activePage.closeWithHotkey();
		} else {
			return true;
		}
	}	
}
