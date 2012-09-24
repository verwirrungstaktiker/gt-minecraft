/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.editor.gui;

import static com.google.common.collect.Sets.*;
import gt.editor.EditorFacade;
import gt.plugin.meta.Hello;

import java.util.Set;

import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.player.SpoutPlayer;

public abstract class OverlayPage {

	protected final EditorOverlay overlay;
	protected final SpoutPlayer player;
	protected final EditorFacade facade;
	
	private final Set<Widget> attachedWidgets = newHashSet();
	
	/**
	 * @param overlay the currently open overlay
	 * @param player the player who opens the overlay
	 * @param facade the facade of the editor
	 */
	protected OverlayPage(final EditorOverlay overlay, final SpoutPlayer player, final EditorFacade facade) {
		this.overlay = overlay;
		this.player = player;
		this.facade = facade;
	}
	
	/**
	 * @param widget the widget to be attached
	 */
	protected void attachWidget(final Widget widget) {
		overlay.attachWidget(Hello.getPlugin(), widget);
		attachedWidgets.add(widget);
	}
	
	/**
	 * called when the page is shown
	 */
	protected abstract void setup();
	
	/**
	 * called when the page is hidden
	 */
	protected abstract void dispose();	
	
	/**
	 * opens the page
	 */
	public void show() {
		setup();
	}

	/**
	 * closes the page
	 */
	public void hide() {
		
		dispose();
		
		for(Widget widget : attachedWidgets) {
			overlay.removeWidget(widget);
		}
	}

	/** 
	 * @return true if it is allowed to close the page with the hotkey
	 */
	public abstract boolean closeWithHotkey();
}
