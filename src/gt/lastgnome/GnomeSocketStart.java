/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
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
public class GnomeSocketStart extends WorldUniqueBlock {

	
	public static final String FILENAME = "start_socket.yml";
	private final AbstractLastGnomeGame game;
	
	/**
	 * @param game the game for which the new start socket is generated
	 */
	public GnomeSocketStart(final AbstractLastGnomeGame game) {
		super(game.getWorldInstance().getWorld(), CustomBlockType.GNOME_START_BLOCK.getObservableCustomBlock());
		
		this.game = game;
	}
	
	@Override
	public void onBlockEvent(final BlockEvent blockEvent) {
		super.onBlockEvent(blockEvent);
		
		if(blockEvent.getBlockEventType() == BlockEventType.BLOCK_INTERACT) {
			game.onStartSocketInteract(blockEvent.getPlayer());
		}
		
	}
}
