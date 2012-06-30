package gt.lastgnome;

import org.bukkit.entity.Player;

import gt.general.world.ObservableBlock;
import gt.general.world.ObservableBlock.BlockEvent;
import gt.general.world.ObservableBlock.BlockEventType;
import gt.general.world.WorldUniqueBlock;
import gt.lastgnome.game.AbstractLastGnomeGame;
import gt.lastgnome.game.LastGnomeGame;

/**
 * Item-class for GnomeSocket
 */
public class GnomeSocketStart extends WorldUniqueBlock {

	
	public static final ObservableBlock START_BLOCK;
	static {
		START_BLOCK = new ObservableBlock("start_socket",
										  "http://dl.dropbox.com/u/29386658/gt/textures/gnome_socket_start_16x16.png",
										  16);
	}

	private final AbstractLastGnomeGame game;
	
	public GnomeSocketStart(final AbstractLastGnomeGame game2) {
		super(game2.getWorldInstance().getWorld(), START_BLOCK);
		
		this.game = game2;
	}
	
	@Override
	public void onBlockEvent(final BlockEvent blockEvent) {
		super.onBlockEvent(blockEvent);
		
		if(blockEvent.blockEventType == BlockEventType.BLOCK_INTERACT
				&& blockEvent.entity instanceof Player) {
			game.onStartSocketInteract((Player) blockEvent.entity);
		}
		
	}
}
