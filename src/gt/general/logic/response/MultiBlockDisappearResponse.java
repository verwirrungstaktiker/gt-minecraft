package gt.general.logic.response;

import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.exceptions.PersistanceException;

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
	private Location min;
	private Location max;
	
	public MultiBlockDisappearResponse() {
		super();
	}
	
	public MultiBlockDisappearResponse(final Block block1, final Block block2) {
		super("multi_block_disappear");
		
		material = block1.getType();
		inverted = false;
		
		loadLocations(block1.getLocation(), block2.getLocation());
		loadBlocks(block1.getWorld());
	}
	
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
		
		min = new Location(loc1.getWorld(), x1, y1, z1);
		max = new Location(loc1.getWorld(), x2, y2, z2);
	}

	@Override
	public void triggered(final boolean active) {
		for (Block block : blocks) {
			if(active ^ inverted) {
				// block disappear
				block.setType(Material.AIR);
			} else {
				// block appear
				block.setType(material);
			}
			// play a fancy effect
			block.getWorld().playEffect(block.getLocation(), Effect.POTION_BREAK, 10);
		}		
	}

	@Override
	public void dispose() {
		for (Block block : blocks) {
			block.setType(Material.AIR);
		}
	}

	@Override
	public PersistanceMap dump() {
		PersistanceMap map = new PersistanceMap();
		
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

	public void loadBlocks(final World world) {
		for (int x=min.getBlockX();x<=max.getBlockX();x++) {
			for (int y=min.getBlockY();y<=max.getBlockY();y++) {
				for (int z=min.getBlockZ();z<=max.getBlockZ();z++) {
					Block block = world.getBlockAt(x, y, z);
					blocks.add(block);					
				}				
			}			
		}		
	}
	
	@Override
	public void setup(final PersistanceMap values, final World world)
			throws PersistanceException {
		
		inverted = values.get(KEY_INVERTED);
		material = values.get(KEY_MATERIAL);
		
		min = values.getLocation(KEY_LOC_MIN, world);
		max = values.getLocation(KEY_LOC_MAX, world);
		
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

	public boolean isInverted() {
		return inverted;
	}

	public void setInverted(final boolean inverted) {
		this.inverted = inverted;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(final Material material) {
		this.material = material;
	}	

}
