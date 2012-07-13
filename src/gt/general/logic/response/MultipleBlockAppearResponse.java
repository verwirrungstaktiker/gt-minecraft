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

public class MultipleBlockAppearResponse extends Response {

	private HashSet<Block> blocks;
	private boolean inverted = false;		//true if block disappears on trigger
	private static final String KEY_INVERTED = "inverted";
	private static final String KEY_MATERIAL = "material";
	private static final String KEY_LOC1 = "location1";
	private static final String KEY_LOC2 = "location2";
	private Material material;
	private Location loc1;
	private Location loc2;
	
	@Override
	public void triggered(boolean active) {
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public PersistanceMap dump() {
		PersistanceMap map = new PersistanceMap();
		
		map.put(KEY_INVERTED, inverted);
		map.put(KEY_MATERIAL, material);
		map.put(KEY_LOC1, loc1);
		map.put(KEY_LOC2, loc2);
		return map;		
	}

	@Override
	public Set<Block> getBlocks() {
		return blocks;
	}

	public void makeBlocks(World world) {
		for (int x=loc1.getBlockX();x<=loc2.getBlockX();++x) {
			for (int y=loc1.getBlockY();y<=loc2.getBlockY();++y) {
				for (int z=loc1.getBlockZ();z<=loc2.getBlockZ();++z) {
					Block block = world.getBlockAt(x, y, z);
					blocks.add(block);					
				}				
			}			
		}		
	}
	
	@Override
	public void setup(PersistanceMap values, World world)
			throws PersistanceException {
		loc1 = values.get(KEY_LOC1);
		loc2 = values.get(KEY_LOC2);
		blocks = new HashSet<Block>();
		makeBlocks(world);
		
		inverted = values.get(KEY_INVERTED);
		material = values.get(KEY_MATERIAL);
		
		if(!inverted) {
			for (Block block : blocks) {
				block.setType(Material.AIR);
			}
			
		}
	}

	public boolean isInverted() {
		return inverted;
	}

	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public void setLoc1(Location loc1) {
		this.loc1 = loc1;
	}

	public void setLoc2(Location loc2) {
		this.loc2 = loc2;
	}
	
	

}
