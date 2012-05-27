package gt.general.gui;

import gt.general.Hero;
import gt.general.HeroObserver;

public interface GuiElement extends HeroObserver{
	
	public void attach(final Hero hero) ;
	
	public void detach(final Hero hero);

}
