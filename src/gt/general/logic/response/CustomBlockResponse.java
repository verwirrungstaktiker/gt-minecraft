/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic.response;

import gt.general.logic.persistence.exceptions.PersistenceException;
import gt.general.world.WorldInstance;
import gt.plugin.meta.CustomBlockType;

import org.bukkit.block.Block;

public abstract class CustomBlockResponse extends BlockResponse {

	private final CustomBlockType type;
	
	/**
	 * @param name name prefix
	 * @param block bukkit block
	 * @param type type of customBlock
	 */
	public CustomBlockResponse(final String name, final Block block, final CustomBlockType type) {
		super("teleport", block);
		
		this.type = type;
	}

	/**
	 * don't delete this anonymous constructor
	 * @param type type of customBlock
	 */
	public CustomBlockResponse(final CustomBlockType type) {
		this.type = type;
	}
	
	
	@Override
	public void setup(final String file, final WorldInstance worldInstance) throws PersistenceException {
		super.setup(file, worldInstance);
		
		type.place(getBlock());
	}
}
