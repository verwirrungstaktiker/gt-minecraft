package gt.general.trigger;

import static com.google.common.collect.Sets.*;
import gt.general.trigger.persistance.PersistanceMap;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;

/**
 *  Represents any Trigger that has exactly 1 corresponding Block
 * 
 * @author roman, sebastian
 */
public abstract class BlockTrigger extends Trigger implements Listener {
	
	public static final String KEY_MATERIAL = "material";
	public static final String KEY_BLOCK = "block";
	
	private Block block;
	private Material material;
	
	public BlockTrigger(final String prefix, final Block block) {
		super(prefix);
		
		this.block = block;
		this.material = block.getType();
	}
	
	public BlockTrigger() {
		super();
	}

	
	@Override
	public void setup(final PersistanceMap values, final World world) {

		material = values.get(KEY_MATERIAL);
				
		block = values.getBlock(KEY_BLOCK, world);
		block.setType(material);
	}

	@Override
	public PersistanceMap dump() {
		PersistanceMap map = new PersistanceMap();
		
		map.put(KEY_BLOCK, block);
		map.put(KEY_MATERIAL, material);

		return map;
	}

	@Override
	public void dispose() {
		getBlock().setType(Material.AIR);
	}
	
	
	/**
	 * @return the block to be watched
	 */
	protected Block getBlock() {
		return block;
	}
	
	/**
	 * @return the material of this block
	 */
	protected Material getMaterial() {
		return material;
	}
	
	/**
	 * @return set with the corresponding block
	 */
	public Set<Block> getBlocks() {
		HashSet<Block> blockSet = newHashSet();
		blockSet.add(block);
		
		return blockSet;
	}
}
