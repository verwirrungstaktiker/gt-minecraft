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
	
	protected OverlayPage(final EditorOverlay overlay, final SpoutPlayer player, final EditorFacade facade) {
		this.overlay = overlay;
		this.player = player;
		this.facade = facade;
	}
	
	protected void attachWidget(final Widget widget) {
		overlay.attachWidget(Hello.getPlugin(), widget);
		attachedWidgets.add(widget);
	}
	
	protected abstract void setup();
	protected abstract void dispose();	
	
	public void show() {
		setup();
	}

	public void hide() {
		
		dispose();
		
		for(Widget widget : attachedWidgets) {
			overlay.removeWidget(widget);
		}
	}

	public abstract boolean closeWithHotkey();
}
