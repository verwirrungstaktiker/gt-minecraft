/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic.trigger;

import static com.google.common.collect.Sets.newHashSet;
import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.exceptions.PersistenceException;

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
	
	/**
	 * @param prefix the prefix of the new Trigger
	 * @param block THE block of the trigger
	 */
	public BlockTrigger(final String prefix, final Block block) {
		super(prefix);
		
		this.block = block;
		this.material = block.getType();
	}
	
	/** to be used in persistance*/
	public BlockTrigger() {}

	
	@Override
	public void setup(final PersistenceMap values, final World world) throws PersistenceException {

		material = values.get(KEY_MATERIAL);	
		block = values.getBlock(KEY_BLOCK, world);
		
		block.setType(material);
	}

	@Override
	public PersistenceMap dump() {
		PersistenceMap map = new PersistenceMap();
		
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
		return newHashSet(block);
	}
}
