package gt.lastgnome;

import gt.general.trigger.Trigger;
import gt.general.trigger.TriggerManager;

/**
 * triggers when the gnome is caught
 */
public class TeamLostTrigger extends Trigger{
	/** the observed game*/
	private LastGnomeGame game;

	/**
	 * @param game the game to be observed
	 * @param callback runnable to be called
	 * @param tm the TriggerManager for this trigger
	 */
	public TeamLostTrigger (LastGnomeGame game, Runnable callback, TriggerManager tm) {
		super(false, callback, tm);
		this.game = game;
	}

	@Override
	public void checkTrigger() {
		if (game.getGnomeBearer() == null) return;
		if (game.getGnomeBearer().getPlayer().getHealth() <= 0) {
			tm.deregisterTrigger(this);
			if (callback!= null) {
			callback.run();
			} else {
				game.dispose();
			}
		}

	}

}
