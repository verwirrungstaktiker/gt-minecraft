package gt.lastgnome.gui;

import gt.general.character.CharacterAttributes;
import gt.general.character.Hero;
import gt.general.character.Hero.Notification;
import gt.general.gui.GuiElement;
import gt.lastgnome.GnomeItem;

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;

/**
 * Visualizes the current speed of a Hero.
 * To be used for the GnomeBearer in the LastGnome gamemode
 * 
 * @author sebastian
 */
public class SpeedBar implements GuiElement {
	
	private static final int BASEWIDTH = 100;
	private static final int BASEHEIGHT = 20;
	
	private final GenericLabel label;
	private final GenericGradient background;
	private final GenericGradient bar;
		
	/**
	 * generates a new SpeedBar
	 */
	public SpeedBar () {
		
		background = new GenericGradient();
		background.setColor(new Color(255, 0, 0))
			.setAnchor(WidgetAnchor.TOP_CENTER)
			.setWidth(BASEWIDTH)
			.setHeight(BASEHEIGHT)
			.shiftXPos(- BASEWIDTH / 2)
			.shiftYPos(- BASEHEIGHT / 2)
			.setPriority(RenderPriority.Highest);
		
		
		bar = new GenericGradient();
		bar.setColor(new Color(0, 255, 0))
			.setAnchor(WidgetAnchor.TOP_CENTER)
			.setWidth(BASEWIDTH)
			.setHeight(BASEHEIGHT)
			.shiftXPos(- BASEWIDTH / 2)
			.shiftYPos(-BASEHEIGHT / 2)
			.setPriority(RenderPriority.Normal);
		
		label = new GenericLabel("you got the gnome");
		label.setAlign(WidgetAnchor.TOP_CENTER)
			.setAnchor(WidgetAnchor.TOP_CENTER)
			.setPriority(RenderPriority.Lowest);
		
	}

	@Override
	public void attach(final Hero hero) {
		hero.getGui().attachWidget(label);		
		hero.getGui().attachWidget(bar);
		hero.getGui().attachWidget(background);
		
		setVisible(false);
		
		hero.addObserver(this);
	}

	@Override
	public void detach(final Hero hero) {
		
		hero.removeObserver(this);
		
		hero.getGui().removeWidget(label);
		hero.getGui().removeWidget(bar);
		hero.getGui().removeWidget(background);
	}

	@Override
	public void update(final Hero hero, final Notification notification) {
		
		if(notification == Notification.INVENTORY) {
			
			if( hero.getActiveItem() instanceof GnomeItem) {
				hero.getPlayer().sendMessage("you got the gnome");
				setVisible(true);
			} else {
				setVisible(false);
			}
			
		} else if (notification == Notification.ATTRIBUTES) {
			
			// TODO maybe this should be done during the processing of the attributes
			Double speed = Math.max(hero.getAttribute(CharacterAttributes.SPEED),
								0.0);
			
			label.setText(Double.toString(speed))
				.setDirty(true);
			
			bar.setWidth((int)((BASEWIDTH-2) * speed))
				.setDirty(true);

		}
	}

	/**
	 * @param state The visibility of all elements of the speedbar
	 */
	private void setVisible(final boolean state) {
		label.setVisible(state);
		bar.setVisible(state);
		background.setVisible(state);
	}
}
