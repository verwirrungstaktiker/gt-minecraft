package gt.general.trigger;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;

/**
 *  Represents any Trigger that has exactly 1 corresponding Block
 * 
 * @author roman
 */
public abstract class BlockTrigger extends Trigger implements Listener {
	protected Block trigger;
	protected Material material;
	
	public BlockTrigger(String prefix, Block block) {
		super(prefix);
		
		this.trigger = block;
		this.material = block.getType();
	}
	
	public BlockTrigger() {
		super();
	}

	
	/**
	 * @return the block to be watched
	 */
	protected Block getBlock() {
		return trigger;
	}
	
	/**
	 * @return set with the corresponding block
	 */
	public Set<Block> getBlocks() {
		HashSet<Block> blockSet = new HashSet<Block>();
		blockSet.add(trigger);
		
		return blockSet;
	}

}
