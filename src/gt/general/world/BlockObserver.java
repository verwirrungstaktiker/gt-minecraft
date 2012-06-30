package gt.general.world;

import gt.general.world.ObservableCustomBlock.BlockEvent;

public interface BlockObserver {

	void onBlockEvent(BlockEvent blockEvent);

}
