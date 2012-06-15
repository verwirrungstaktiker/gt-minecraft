package gt.general.trigger;

import org.bukkit.Location;
import org.bukkit.material.PressureSensor;

public class PlateTrigger extends Trigger{

	//private PressureSensor plate;
	private boolean oldstatus;
	private Runnable onDeactivate;
	private Location loc;
	
	/**public PlateTrigger(PressureSensor plate, boolean repeat, Runnable onActivate, Runnable onDeactivate, TriggerManager tm) {
		super(repeat, onActivate, tm);
		this.plate = plate;
		this.onDeactivate = onDeactivate;

		oldstatus = false;
	}*/

	public PlateTrigger(Location plate, boolean repeat, Runnable onActivate, Runnable onDeactivate, TriggerManager tm) {
		super(repeat, onActivate, tm);
		//this.plate = (PressureSensor) plate.getBlock().getState().getData();
		this.onDeactivate = onDeactivate;
		this.loc = plate;
		oldstatus = false;
	}	
	
	@Override
	public void checkTrigger() {
		PressureSensor plate = (PressureSensor) loc.getBlock().getState().getData();
		if (plate.isPressed() != oldstatus ) {
			oldstatus = plate.isPressed();
			if (plate.isPressed()) {
				callback.run();
			} else {
				onDeactivate.run();
			}
		}
	}

}
