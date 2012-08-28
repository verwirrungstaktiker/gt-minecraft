package gt.general.logic.response;

import gt.general.logic.persistence.exceptions.PersistenceException;
import gt.general.world.WorldInstance;
import gt.plugin.meta.CustomBlockType;

import org.bukkit.Material;
import org.bukkit.block.Block;

public abstract class CustomBlockResponse extends BlockResponse {

	private final CustomBlockType type;
	
	public CustomBlockResponse(final String name, final Block block, final CustomBlockType type) {
		super("teleport", block);
		
		this.type = type;
	}

	public CustomBlockResponse(final CustomBlockType type) {
		this.type = type;
	}
	
	
	@Override
	public void setup(final String file, final WorldInstance worldInstance) throws PersistenceException {
		super.setup(file, worldInstance);
		
		type.place(getBlock());
	}
}
