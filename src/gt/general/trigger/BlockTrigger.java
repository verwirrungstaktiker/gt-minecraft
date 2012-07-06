package gt.general.trigger;

import static com.google.common.collect.Maps.*;

import java.util.HashSet;
import java.util.Map;
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
	private Block trigger;
	private Material material;
	
	public BlockTrigger(String prefix, Block block) {
		super(prefix);
		
		this.trigger = block;
		this.material = block.getType();
	}
	
	public BlockTrigger() {
		super();
	}

	
	@Override
	public void setup(final Map<String, Object> values, final World world) {

		material = (Material) values.get("material");
				
		trigger = blockFromCoordinates(values, world);
		trigger.setType(material);
		
	}

	@Override
	public Map<String, Object> dump() {
		Map<String, Object> map = newHashMap();
		
		map.putAll(coordinatesFromBlock(trigger));
		map.put("material", material);
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
		return trigger;
	}
	
	protected Material getMaterial() {
		return material;
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
