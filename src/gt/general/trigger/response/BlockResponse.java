package gt.general.trigger.response;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;

/**
 *  Represents any Response that has exactly 1 corresponding Block
 * 
 * @author roman
 */
public abstract class BlockResponse extends Response implements Listener {
	protected Block block;
	protected Material material;
	
	public BlockResponse(String prefix, Block block) {
		super(prefix);
		
		this.block = block;
		this.material = block.getType();
	}
	
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
	 * @return set with the corresponding block
	 */
	public Set<Block> getBlocks() {
		HashSet<Block> blockSet = new HashSet<Block>();
		blockSet.add(block);
		
		return blockSet;
	}
	
	public void highlight() {
		for(Block block : getBlocks()) {
			block.getWorld().playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES, 25);
		}
	}

}
