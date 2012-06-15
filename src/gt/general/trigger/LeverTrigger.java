package gt.general.trigger;

import org.bukkit.material.Lever;
import org.bukkit.Location;

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
	
	public LeverTrigger(Location lever, boolean repeat, Runnable onActivate, Runnable onDeactivate, TriggerManager tm) {
		super(repeat, onActivate, tm);
		this.lever = (Lever )lever.getBlock().getState().getData();
		this.onDeactivate = onDeactivate;
		oldstatus = false;
	}	

	
	
	@Override
	public void checkTrigger() {
		if (lever.isPowered() != oldstatus ) {
			oldstatus = lever.isPowered();
			if (lever.isPowered()) {
				callback.run();
			} else {
				onDeactivate.run();
			}
		}
	}

}
