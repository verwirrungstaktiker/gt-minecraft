package gt.lastgnome;

import org.bukkit.entity.Player;

import gt.general.world.ObservableCustomBlock;
import gt.general.world.ObservableCustomBlock.BlockEvent;
import gt.general.world.ObservableCustomBlock.BlockEventType;
import gt.general.world.WorldUniqueBlock;
import gt.lastgnome.game.AbstractLastGnomeGame;

/**
 * Item-class for GnomeSocket
 */
public class GnomeSocketEnd extends WorldUniqueBlock {

	public static final String FILENAME = "end_socket.yml";

	
	public static final ObservableCustomBlock END_BLOCK;
	
	static {		
		END_BLOCK = new ObservableCustomBlock("end_socket",
				"http://dl.dropbox.com/u/29386658/gt/textures/gnome_socket_end_16x16.png",
				16);
	}

	private final AbstractLastGnomeGame game;
	
	public GnomeSocketEnd(final AbstractLastGnomeGame game) {
		super(game.getWorldInstance().getWorld(), END_BLOCK);
		
		this.game = game;
	}
	
	@Override
	public void onBlockEvent(final BlockEvent blockEvent) {
		super.onBlockEvent(blockEvent);
		
		if(blockEvent.blockEventType == BlockEventType.BLOCK_INTERACT
				&& blockEvent.entity instanceof Player) {
			game.onEndSocketInteract((Player) blockEvent.entity);
		}
		
	}
}
