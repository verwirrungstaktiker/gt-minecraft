package gt.lastgnome;

import gt.general.world.ObservableCustomBlock.BlockEvent;
import gt.general.world.ObservableCustomBlock.BlockEventType;
import gt.general.world.WorldUniqueBlock;
import gt.lastgnome.game.AbstractLastGnomeGame;
import gt.plugin.meta.CustomBlockType;

import org.bukkit.entity.Player;

/**
 * Item-class for GnomeSocket
 */
public class GnomeSocketStart extends WorldUniqueBlock {

	
	public static final String FILENAME = "start_socket.yml";
	private final AbstractLastGnomeGame game;
	
	public GnomeSocketStart(final AbstractLastGnomeGame game2) {
		super(game2.getWorldInstance().getWorld(), CustomBlockType.GNOME_START_BLOCK.getObservableCustomBlock());
		
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
