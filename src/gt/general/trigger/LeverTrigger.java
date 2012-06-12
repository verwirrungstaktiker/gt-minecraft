package gt.general.trigger;

import org.bukkit.material.Lever;

public class LeverTrigger extends Trigger{

	private Lever lever;
	private boolean oldstatus;
	private Runnable onDeactivate;
	
	public LeverTrigger(Lever lever, boolean repeat, Runnable onActivate, Runnable onDeactivate, TriggerManager tm) {
		super(repeat, onActivate, tm);
		this.lever = lever;
		this.onDeactivate = onDeactivate;
		oldstatus = false;
	}

	
	
	@Override
	public void checkTrigger() {
		if (lever.isPowered() != oldstatus ) {
			if (lever.isPowered()) {
				callback.run();
			} else {
				onDeactivate.run();
			}
		}
	}

}
