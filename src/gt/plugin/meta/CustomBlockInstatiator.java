package gt.plugin.meta;

import gt.general.world.ObservableCustomBlock;

public abstract class CustomBlockInstatiator {
	/**
	 * @return a newly instantiated block
	 */
	public abstract ObservableCustomBlock instantiate();
}
