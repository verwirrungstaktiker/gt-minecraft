package gt.general.trigger;

import org.bukkit.block.Block;

/**
 * uses a minecraft lever or stone button as trigger
 * 
 * @author roman
 */
public abstract class AttachableRedstoneTrigger extends RedstoneTrigger {
	
	/**
	 * @param trigger the lever to be used as trigger
	 */
	public AttachableRedstoneTrigger(final String labelPrefix, final Block trigger) {
		super(labelPrefix, trigger);
	}
	
	public AttachableRedstoneTrigger() {
		
	}
}
