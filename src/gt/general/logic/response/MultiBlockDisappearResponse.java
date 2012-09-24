/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.logic.response;

import gt.general.logic.TriggerEvent;
import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.exceptions.PersistenceException;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class MultiBlockDisappearResponse extends Response {

	private HashSet<Block> blocks;
	private boolean inverted = false;		//true if block appears on trigger
	private static final String KEY_INVERTED = "inverted";
	private static final String KEY_MATERIAL = "material";
	private static final String KEY_LOC_MIN = "min";
	private static final String KEY_LOC_MAX = "max";
	private Material material;
	private Block min;
	private Block max;
	
	/**
	 * don't delete this anonymous constructor
	 */
	public MultiBlockDisappearResponse() {
		super();
	}
	
	/**
	 * @param block1 lower edge of the area
	 * @param block2 upper edge of the area
	 */
	public MultiBlockDisappearResponse(final Block block1, final Block block2) {
		super("multi_block_disappear");
		
		material = block1.getType();
		inverted = false;
		
		loadLocations(block1.getLocation(), block2.getLocation());
		loadBlocks(block1.getWorld());
	}
	
	/**
	 * @param loc1 location of the first block
	 * @param loc2 location of the second block
	 */
	private void loadLocations(final Location loc1, final Location loc2) {
		int x1, x2, y1, y2, z1, z2, temp;
		
		x1 = loc1.getBlockX();
		y1 = loc1.getBlockY();
		z1 = loc1.getBlockZ();
		x2 = loc2.getBlockX();
		y2 = loc2.getBlockY();
		z2 = loc2.getBlockZ();
		
		if(x2 > x1) {
			temp = x1;
			x1 = x2;
			x2 = temp;
		}
		
		if(y2 > y1) {
			temp = y1;
			y1 = y2;
			y2 = temp;
		}
		
		if(z2 > z1) {
			temp = z1;
			z1 = z2;
			z2 = temp;
		}
		
		min = loc1.getWorld().getBlockAt(x1, y1, z1);
		max = loc1.getWorld().getBlockAt(x2, y2, z2);
	}

	@Override
	public void triggered(final TriggerEvent e) {
		for (Block block : blocks) {
			if(e.isActive() ^ inverted) {
				// block disappear
				block.setType(Material.AIR);
			} else {
				// block appear
				block.setType(material);
			}
			// play a fancy effect
			block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, Response.EFFECT_RANGE);
		}		
	}

	@Override
	public void dispose() {
		for (Block block : blocks) {
			block.setType(Material.AIR);
		}
	}

	@Override
	public PersistenceMap dump() {
		PersistenceMap map = new PersistenceMap();
		
		map.put(KEY_INVERTED, inverted);
		map.put(KEY_MATERIAL, material);
		map.put(KEY_LOC_MIN, min);
		map.put(KEY_LOC_MAX, max);
		return map;		
	}

	@Override
	public Set<Block> getBlocks() {
		return blocks;
	}

	/**
	 * spawn all blocks
	 * @param world the world that holds the blocks
	 */
	public void loadBlocks(final World world) {
		for (int x=min.getX();x<=max.getX();x++) {
			for (int y=min.getY();y<=max.getY();y++) {
				for (int z=min.getZ();z<=max.getZ();z++) {
					Block block = world.getBlockAt(x, y, z);
					blocks.add(block);					
				}				
			}			
		}		
	}
	
	@Override
	public void setup(final PersistenceMap values, final World world)
			throws PersistenceException {
		
		inverted = values.get(KEY_INVERTED);
		material = values.get(KEY_MATERIAL);
		
		min = values.getBlock(KEY_LOC_MIN, world);
		max = values.getBlock(KEY_LOC_MAX, world);
		
		blocks = new HashSet<Block>();
		
		loadBlocks(world);
		
		if(inverted) {
			for (Block block : blocks) {
				block.setType(Material.AIR);
			}
		} else {
			for (Block block : blocks) {
				block.setType(material);
			}
		}
	}

	/**
	 * @return true if the blocks appear on power
	 */
	public boolean isInverted() {
		return inverted;
	}

	/**
	 * @param inverted true if the blocks shall appear on power
	 */
	public void setInverted(final boolean inverted) {
		this.inverted = inverted;
	}

	/**
	 * @return the Material of the Blocks
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * @param material the Material of the Blocks
	 */
	public void setMaterial(final Material material) {
		this.material = material;
	}	

}
