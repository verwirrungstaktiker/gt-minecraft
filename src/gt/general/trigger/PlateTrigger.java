package gt.general.trigger;

import org.bukkit.material.PressurePlate;
import org.bukkit.material.PressureSensor;

public class PlateTrigger extends Trigger{

	private PressureSensor plate;
	private boolean oldstatus;
	private Runnable onDeactivate;
	
	public PlateTrigger(PressurePlate plate, boolean repeat, Runnable onActivate, Runnable onDeactivate, TriggerManager tm) {
		super(repeat, onActivate, tm);
		this.plate = plate;
		this.onDeactivate = onDeactivate;

		oldstatus = false;
	}

	
	
	@Override
	public void checkTrigger() {
		if (plate.isPressed() != oldstatus ) {
			if (plate.isPressed()) {
				callback.run();
			} else {
				onDeactivate.run();
			}
		}
	}

}
