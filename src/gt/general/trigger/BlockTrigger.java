package gt.general.trigger;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.particle.Particle;
import org.getspout.spoutapi.particle.Particle.ParticleType;

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
	
	public void highlight() {
		for(Block block : getBlocks()) {
//			block.getWorld().playEffect(block.getLocation(), Effect.ENDER_SIGNAL, 25);
			
		}
	}

}
