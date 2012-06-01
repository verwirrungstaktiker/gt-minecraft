package gt.general.gui;

import gt.general.Hero;
import gt.general.HeroObserver;

public interface GuiElement extends HeroObserver{
	
	/**
	 * @param hero to be called if attached to a Hero
	 */
	void attach(final Hero hero) ;
	
	
	/**
	 * @param hero to be called detached from a Hero
	 */
	void detach(final Hero hero);

}
