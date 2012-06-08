package gt.general.gui;

import gt.general.character.Hero;

public interface GuiElement{
	
	/**
	 * @param hero to be called if attached to a Hero
	 */
	void attach(final Hero hero) ;
	
	
	/**
	 * @param hero to be called detached from a Hero
	 */
	void detach(final Hero hero);

}
