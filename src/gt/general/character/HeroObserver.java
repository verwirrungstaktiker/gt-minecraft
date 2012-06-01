package gt.general.character;


public interface HeroObserver {

	/**
	 * To be called if the Hero is changed.
	 * 
	 * @param hero The Hero to be observed.
	 * @param notification The type of change which happened.
	 */
	void update(final Hero hero, final Hero.Notification notification);
	
}
