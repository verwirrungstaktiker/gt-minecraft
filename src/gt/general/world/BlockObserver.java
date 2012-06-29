package gt.general.world;

import gt.general.world.ObservableBlock.BlockEvent;

public interface BlockObserver {

	void onBlockEvent(BlockEvent blockEvent);

}
