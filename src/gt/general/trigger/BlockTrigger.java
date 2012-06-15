package gt.general.trigger;

import gt.general.trigger.callbacks.DeviceCallback;

import org.bukkit.block.Block;

public class BlockTrigger extends Trigger{

	//private PressureSensor plate;
	private boolean oldstatus;
	private DeviceCallback callback;
	private Block block;
	
	/**public PlateTrigger(PressureSensor plate, boolean repeat, Runnable onActivate, Runnable onDeactivate, TriggerManager tm) {
		super(repeat, onActivate, tm);
		this.plate = plate;
		this.onDeactivate = onDeactivate;

		oldstatus = false;
	}*/

	public BlockTrigger(Block triggerblock, boolean repeat, DeviceCallback callback, TriggerManager tm) {
		super(repeat, null, tm);
		//this.plate = (PressureSensor) plate.getBlock().getState().getData();
		this.callback = callback;
		this.block = triggerblock;
		oldstatus = false;
	}	
	
	@Override
	public void checkTrigger() {
		//PressureSensor plate = (PressureSensor) loc.getBlock().getState().getData();
		boolean powered = (block.getBlockPower() > 0);
		
		if (powered != oldstatus ) {
			oldstatus = powered;
			if (powered) {
				callback.onEnable();
			} else {
				callback.onDisable();
			}
			
			if (!repeat) {
				tm.deregisterTrigger(this);
			}
		}
	}

}
