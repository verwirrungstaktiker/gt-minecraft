package gt.lastgnome;

import gt.general.world.ObservableBlock;
import gt.general.world.ObservableBlock.BlockEvent;
import gt.general.world.ObservableBlock.BlockEventType;
import gt.general.world.WorldUniqueBlock;

/**
 * Item-class for GnomeSocket
 */
public class GnomeSocketEnd extends WorldUniqueBlock {

	public static final ObservableBlock END_BLOCK;
	
	static {		
		END_BLOCK = new ObservableBlock("end_socket",
				"http://dl.dropbox.com/u/29386658/gt/textures/gnome_socket_end_16x16.png",
				16);
	}

	private final LastGnomeGame game;
	
	public GnomeSocketEnd(final LastGnomeGame game) {
		super(game.getWorldInstance().getWorld(), END_BLOCK);
		
		this.game = game;
	}
	
	@Override
	public void onBlockEvent(final BlockEvent blockEvent) {
		super.onBlockEvent(blockEvent);
		
		if(blockEvent.blockEventType == BlockEventType.BLOCK_INTERACT) {
			// TODO game.onStartBlockInteract(blockEvent.entity)
		}
		
	}
}
