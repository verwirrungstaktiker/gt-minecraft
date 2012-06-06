package gt.general.gui;

import gt.general.character.Hero;
import gt.plugin.helloworld.HelloWorld;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;

import org.getspout.spoutapi.gui.Widget;

public class HeroGui {

	// XXX do we really need this set?
	private final Map<GuiElementType, GuiElement> guiElements;
	
	private final Hero holder;
	
	/**
	 * @param holder To which hero this Gui is coupled
	 */
	public HeroGui (final Hero holder) {
		guiElements = new EnumMap<GuiElementType, GuiElement>(GuiElementType.class);
		this.holder = holder;
	}
	
	/**
	 * @param ge The GuiElement to be added to this Gui
	 */
	public void addGuiElement(final GuiElementType get, final GuiElement ge) {
		
		/*
		 * TODO catch if there is already more than one element
		 */
		
		guiElements.put(get, ge);
		ge.attach(holder);
	}
	
	/**
	 * @param ge The GuiElement to be removed from this Gui
	 */
	public void removeGuiElement(final GuiElementType get) {
		
		GuiElement ge = guiElements.remove(get);
		holder.removeObserver(ge);
	}
	
	/**
	 * @param ge The GuiElement to be checked
	 * @return true if the GuiElement is displayed on this Gui
	 */
	public boolean hasGuiElement(final GuiElementType get) {
		return guiElements.containsKey(get);
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
