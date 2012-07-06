package gt.general.gui;

import gt.general.character.Hero;
import gt.plugin.meta.Hello;

import java.util.EnumMap;
import java.util.Map;

import org.getspout.spoutapi.gui.InGameHUD;
import org.getspout.spoutapi.gui.PopupScreen;
import org.getspout.spoutapi.gui.Widget;

public class HeroGui {

	// XXX do we really need this set?
	private final Map<GuiElementType, GuiElement> guiElements;
	
	private final Hero holder;
	
	private final Prompt prompt;
	
	/**
	 * @param holder To which hero this Gui is coupled
	 */
	public HeroGui (final Hero holder) {
		guiElements = new EnumMap<GuiElementType, GuiElement>(GuiElementType.class);
		this.holder = holder;
		
		prompt = new Prompt(holder);
	}
	
	/**
	 * @param get The type of the gui element to be added
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
	 * @param get The type of the GuiElement to be removed from this Gui
	 */
	public void removeGuiElement(final GuiElementType get) {
		
		GuiElement ge = guiElements.remove(get);
		ge.detach(holder);
	}
	
	/**
	 * @param get The GuiElementType to be checked
	 * @return true if the GuiElement is displayed on this Gui
	 */
	public boolean hasGuiElement(final GuiElementType get) {
		return guiElements.containsKey(get);
	}
	
	/**
	 * Handles the connection to the Spout core
	 * 
	 * @param widgets To be attached to the Heros screen.
	 */
	public void attachWidgets(final Widget... widgets) {
		for(Widget widget : widgets) {
			getMainScreen()
				.attachWidget(Hello.getPlugin(), widget);
		}
	}
	
	/**
	 * Handles the connention to the Spout core
	 * 
	 * @param widgets To be removed from the Heros screen.
	 */
	public void removeWidgets(final Widget... widgets) {
		for(Widget widget : widgets) {
			getMainScreen()
				.removeWidget(widget);
		}
	}
	
	public void prompt(String message) {
		
		prompt.setMessage(message);
		
		getMainScreen()
			.attachPopupScreen(prompt.getPopup());
	}
	
	public void closePrompt() {
		closePopup(prompt.getPopup());
	}
	
	public void closePopup(PopupScreen popup) {
		if(getMainScreen().getActivePopup().equals(popup)) {
			getMainScreen()
				.closePopup();
		}
	}
	
	/**
	 * @return the main screen of the holder
	 */
	private InGameHUD getMainScreen() {
		return	holder.getSpoutPlayer().getMainScreen();
	}
}
