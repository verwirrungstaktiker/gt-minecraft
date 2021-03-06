/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.lastgnome;

import gt.general.world.ObservableCustomBlock.BlockEvent;
import gt.general.world.ObservableCustomBlock.BlockEventType;
import gt.general.world.WorldUniqueBlock;
import gt.lastgnome.game.AbstractLastGnomeGame;
import gt.plugin.meta.CustomBlockType;

/**
 * Item-class for GnomeSocket
 */
public class GnomeSocketEnd extends WorldUniqueBlock {

	public static final String FILENAME = "end_socket.yml";

	private final AbstractLastGnomeGame game;

	/**
	 * @param game the game for which the new end socket is generated
	 */
	public GnomeSocketEnd(final AbstractLastGnomeGame game) {
		super(game.getWorldInstance().getWorld(),
				CustomBlockType.GNOME_END_BLOCK.getObservableCustomBlock());

		this.game = game;
	}

	@Override
	public void onBlockEvent(final BlockEvent blockEvent) {
		super.onBlockEvent(blockEvent);

		if (blockEvent.getBlockEventType() == BlockEventType.BLOCK_INTERACT) {
			game.onEndSocketInteract(blockEvent.getPlayer());
		}

	}
}
