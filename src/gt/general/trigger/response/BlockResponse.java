package gt.general.trigger.response;

import static com.google.common.collect.Maps.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Effect;
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
	
	public void highlight() {
		for(Block block : getBlocks()) {
			block.getWorld().playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES, 25);
		}
	}
	
	@Override
	public void setup(final Map<String, Object> values, final World world) {
		
		material = (Material) values.get("material");
		
		block = blockFromCoordinates(values, world);
		block.setType(material);
	}

	@Override
	public Map<String, Object> dump() {
		Map<String, Object> map = newHashMap();
		
		map.put("material", material);
		map.putAll(coordinatesFromBlock(block));
		
		return map;
	}
	
	@Override
	public void  dispose() {
		block.setType(Material.AIR);
	}
	
}
