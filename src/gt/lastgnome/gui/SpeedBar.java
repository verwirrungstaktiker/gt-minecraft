package gt.lastgnome.gui;

import gt.general.Hero;
import gt.general.gui.GuiElement;
import gt.plugin.helloworld.HelloWorld;

import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.WidgetAnchor;

public class SpeedBar implements GuiElement{

	private Hero holder;
	
	private final GenericLabel gl;
	
	public SpeedBar () {
		
		gl =  new GenericLabel("you got the gnome");
		gl.setAuto(true);//.setWidth(100).setHeight(30);
		gl.setAlign(WidgetAnchor.CENTER_CENTER).setAnchor(WidgetAnchor.CENTER_CENTER);
	}

	@Override
	public void attach(final Hero hero) {
		detach();
		
		holder = hero;
		holder.getSpoutPlayer().getMainScreen().attachWidget(HelloWorld.getPlugin(), gl);
	}

	@Override
	public void detach() {
		if(holder != null) {
			holder.getSpoutPlayer().getMainScreen().removeWidget(gl);
			holder = null;
		}
	}
}
