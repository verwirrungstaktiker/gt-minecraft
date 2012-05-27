package gt.lastgnome.gui;

import gt.general.CharacterAttributes;
import gt.general.Hero;
import gt.general.Hero.Notification;
import gt.general.gui.GuiElement;
import gt.lastgnome.GnomeItem;

import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.WidgetAnchor;

public class SpeedBar implements GuiElement{
	
	private final GenericLabel gl;
	
	public SpeedBar () {
		gl =  new GenericLabel("you got the gnome");
		gl.setAuto(true);//.setWidth(100).setHeight(30);
		gl.setAlign(WidgetAnchor.CENTER_CENTER).setAnchor(WidgetAnchor.CENTER_CENTER);
	}

	@Override
	public void attach(final Hero hero) {		
		hero.getGui().attachWidget(gl);
		gl.setVisible(false);
	}

	@Override
	public void detach(final Hero hero) {
		hero.getGui().removeWidget(gl);
	}

	@Override
	public void update(Hero hero, Notification notification) {
		
		if(notification == Notification.INVENTORY) {
			
			if( hero.getActiveItem() instanceof GnomeItem) {
				hero.getPlayer().sendMessage("you got the gnome");
				gl.setVisible(true);
			} else {
				gl.setVisible(false);
			}
			
		} else if (notification == Notification.ATTRIBUTES) {
			gl.setText(Double.toString(hero.getAttribute(CharacterAttributes.SPEED)));
		}
	}
}
