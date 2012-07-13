package gt.general.logic.response;

import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.exceptions.PersistanceException;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;

/**
 *  Represents any Response that has exactly 1 corresponding Block
 * 
 * @author roman
 */
public abstract class BlockResponse extends Response implements Listener {
	private Block block;
	private Material material;
	
	protected static final String KEY_LOCATION = "location";
	protected static final String KEY_MATERIAL = "material";
	
	/**
	 * @param prefix label prefix
	 * @param block bukkit block
	 */
	public BlockResponse(final String prefix, final Block block) {
		super(prefix);
		
		this.block = block;
		this.material = block.getType();
	}
	
	/**
	 * anonymous constructor (don't delete)
	 */
	public BlockResponse() {
		super();
	}

	
	/**
	 * @return the corresponding bukkit block
	 */
	public Block getBlock() {
		return block;
	}
	
	/**
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}
	
	/**
	 * @return set with the corresponding block
	 */
	public Set<Block> getBlocks() {
		HashSet<Block> blockSet = new HashSet<Block>();
		blockSet.add(block);
		
		return blockSet;
	}
	
	@Override
	public void setup(final PersistanceMap values, final World world) throws PersistanceException {
		
		material = values.get(KEY_MATERIAL);
		
		block = values.getBlock(KEY_LOCATION, world);
		block.setType(material);
	}

	@Override
	public PersistanceMap dump() {
		PersistanceMap map = new PersistanceMap();
		
		map.put(KEY_MATERIAL, material);
		map.put(KEY_LOCATION, block);
		
		return map;
	}
	
	@Override
	public void  dispose() {
		block.setType(Material.AIR);
	}
	
}
