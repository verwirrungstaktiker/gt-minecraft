package gt.general.gui;

import gt.general.character.Hero;
import gt.plugin.helloworld.HelloWorld;

import java.util.HashSet;
import java.util.Set;

import org.getspout.spoutapi.gui.Widget;

public class HeroGui {

	// XXX do we really need this set?
	private final Set<GuiElement> guiElements;
	
	private final Hero holder;
	
	/**
	 * @param holder To which hero this Gui is coupled
	 */
	public HeroGui (final Hero holder) {
		guiElements = new HashSet<GuiElement>();
		this.holder = holder;
	}
	
	/**
	 * @param ge The GuiElement to be added to this Gui
	 */
	public void addGuiElement(final GuiElement ge) {
		guiElements.add(ge);
		ge.attach(holder);
		
		holder.addObserver(ge);
	}
	
	/**
	 * @param ge The GuiElement to be removed from this Gui
	 */
	public void removeGuiElement(final GuiElement ge) {
		guiElements.remove(ge);
		holder.removeObserver(ge);
		
		ge.detach(holder);
	}
	
	/**
	 * @param ge The GuiElement to be checked
	 * @return true if the GuiElement is displayed on this Gui
	 */
	public boolean hasGuiElement(final GuiElement ge) {
		return guiElements.contains(ge);
	}
	
	/**
	 * Handles the connection to the Spout core
	 * 
	 * @param widget To be attached to the Heros screen.
	 */
	public void attachWidget(final Widget widget) {
		holder
			.getSpoutPlayer()
			.getMainScreen()
			.attachWidget(HelloWorld.getPlugin(), widget);
	}
	
	/**
	 * Handles the connention to the Spout core
	 * 
	 * @param widget To be removed from the Heros screen.
	 */
	public void removeWidget(final Widget widget) {
		holder
			.getSpoutPlayer()
			.getMainScreen()
			.removeWidget(widget);
	}	
}
