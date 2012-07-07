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
public class GnomeSocketEnd extends WorldUniqueBlock {

	public static final String FILENAME = "end_socket.yml";

	private final AbstractLastGnomeGame game;

	public GnomeSocketEnd(final AbstractLastGnomeGame game) {
		super(game.getWorldInstance().getWorld(),
				CustomBlockType.GNOME_END_BLOCK.getObservableCustomBlock());

		this.game = game;
	}

	@Override
	public void onBlockEvent(final BlockEvent blockEvent) {
		super.onBlockEvent(blockEvent);

		if (blockEvent.blockEventType == BlockEventType.BLOCK_INTERACT
				&& blockEvent.entity instanceof Player) {
			game.onEndSocketInteract((Player) blockEvent.entity);
		}

	}
}
