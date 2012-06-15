package gt.general.trigger;

import org.bukkit.block.Block;

import gt.general.trigger.callbacks.DeviceCallback;

public class DeviceTrigger extends Trigger{

	private DeviceCallback callback;
	private Block[] blocks;
	private boolean[] code;
	private boolean oldstatus;
	
	
	public DeviceTrigger(Block[] blocks, boolean[] code, boolean repeat, DeviceCallback callback, TriggerManager tm) {
		super(repeat, null, tm);
		this.blocks = blocks;
		this.code = code;
		this.callback = callback;
		
	}

	@Override
	public void checkTrigger() {
		boolean status = true;
		for (int i=0;i<blocks.length;++i) {
			boolean powered = (blocks[i].getBlockPower() > 0);
			if ( powered != code[i]) {
				status = false;
				break;
			}
		}
		
		if (status != oldstatus) {
			oldstatus = status;
			if (status) {
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
