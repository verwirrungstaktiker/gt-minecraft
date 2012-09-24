/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.gui;

import gt.general.character.Hero;
import gt.general.character.HeroObserver;
import gt.general.character.Hero.Notification;

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericItemWidget;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;

public class HeroFrame implements HeroObserver{

	private static final int BASEHEIGHT = 15;
	private static final int BASEWIDTH = 75;
	
	private final GenericGradient background;
	private final GenericLabel name;
	
	private final TeamFrame teamFrame;
	private int rank;
	
	private static final GenericItemWidget NOITEM = null;
	private GenericItemWidget active = NOITEM;

	/**
	 * @param teamFrame The parent of this HeroFrame.
	 * @param hero The Hero associated with this HeroFrame.
	 */
	public HeroFrame(final TeamFrame teamFrame, final Hero hero) {
		
		this.teamFrame = teamFrame;
		rank = 0;
				
		background = new GenericGradient();
		background
			.setTopColor(new Color(0, 0, 255, 75))
			.setBottomColor(new Color(0, 0, 255))
			.setWidth(BASEWIDTH)
			.setHeight(BASEHEIGHT)
			.setPriority(RenderPriority.Highest);
			
		
		name = new GenericLabel(hero.getPlayer().getName());
		name.setAlign(WidgetAnchor.CENTER_LEFT)
			.setWidth(BASEWIDTH)
			.setHeight(BASEHEIGHT)
			.setX(2)
			.setPriority(RenderPriority.Lowest);
		
		replaceActiveItemWidget(hero);
	}

	@Override
	public void update(final Hero hero, final Notification notification) {
		if (notification == Notification.INVENTORY) {			
			replaceActiveItemWidget(hero);
		}
	}
	
	/**
	 * Updates the active item view of the Hero
	 * @param hero the hero to be updated;
	 */
	private void replaceActiveItemWidget(final Hero hero) {
		
		// detach old item 
		if(teamFrame.isAttached() && active != NOITEM) {
			teamFrame.getHolder().getGui().removeWidgets(active);
		}
		
		// replace Widget
		if(hero.getActiveItem() == null) {
			active = NOITEM;
		} else {
			active = new GenericItemWidget(hero.getActiveItem().getItemStack());
			
			active
				.setHeight(BASEHEIGHT)
				.setWidth(BASEHEIGHT)
				.setX(BASEWIDTH)
				.setY(BASEHEIGHT * rank)
				.setPriority(RenderPriority.Normal);
		}
		
		// attach new item
		if(teamFrame.isAttached() && active != NOITEM) {
			teamFrame.getHolder().getGui().attachWidgets(active);
		}
	}

	/**
	 * Positions the HeroFrame in the context of the TeamFrame.
	 * @param rank 0 is the topmost frame.
	 */
	public void layout(final int rank) {
		this.rank = rank;
		layout();
	}
	
	/**
	 * Implements the position process for the contained widgets
	 */
	private void layout() {
		name.setY(BASEHEIGHT * rank + BASEHEIGHT / 2);
		background.setY(BASEHEIGHT * rank);
		
		if(active != NOITEM) {
			active.setY(BASEHEIGHT * rank);
		}
	}
	
	/**
	 * @param hero To who should the widgets be attached.
	 */
	public void attach(final Hero hero) {
		hero.getGui().attachWidgets(name, background);
		
		if(active != NOITEM) {
			hero.getGui().attachWidgets(active);
		}
		
	}
	
	/**
	 * @param hero From who should the widgets be removed.
	 */
	public void detach(final Hero hero) {
		hero.getGui().removeWidgets(name, background);
		
		if(active != NOITEM) {
			hero.getGui().removeWidgets(active);
		}
	}
}
