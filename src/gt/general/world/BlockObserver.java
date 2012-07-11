package gt.general.world;

import gt.general.world.ObservableCustomBlock.BlockEvent;

public interface BlockObserver {

	/**
	 * @param blockEvent Player interacts with a ObservableCustomBlock
	 */
	void onBlockEvent(BlockEvent blockEvent);

}
