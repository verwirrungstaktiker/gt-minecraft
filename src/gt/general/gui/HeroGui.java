package gt.general.gui;

import gt.general.Hero;
import gt.plugin.helloworld.HelloWorld;

import java.util.HashSet;
import java.util.Set;

import org.getspout.spoutapi.gui.Widget;

public class HeroGui {

	// XXX do we really need this set?
	private final Set<GuiElement> guiElements;
	
	
	private final Hero holder;
	
	public HeroGui (Hero holder) {
		guiElements = new HashSet<GuiElement>();
		this.holder = holder;
	}
	
	public void addGuiElement(GuiElement ge) {
		guiElements.add(ge);
		ge.attach(holder);
		
		holder.addObserver(ge);
	}
	
	public void removeGuiElement(GuiElement ge) {
		guiElements.remove(ge);
		holder.removeObserver(ge);
		
		ge.detach(holder);
	}
	
	public boolean hasGuiElement(GuiElement ge) {
		return guiElements.contains(ge);
	}
	
	public void attachWidget(Widget widget) {
		holder
			.getSpoutPlayer()
			.getMainScreen()
			.attachWidget(HelloWorld.getPlugin(), widget);
	}
	
	public void removeWidget(Widget widget) {
		holder
			.getSpoutPlayer()
			.getMainScreen()
			.removeWidget(widget);
	}	
}
