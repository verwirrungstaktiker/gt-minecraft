package gt.general.world;

import static com.google.common.collect.Sets.newHashSet;

import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.YamlSerializable;
import gt.general.logic.persistence.exceptions.PersistenceException;
import gt.general.world.ObservableCustomBlock.BlockEvent;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;

public class WorldUniqueBlock extends YamlSerializable implements BlockObserver {

	private final World world;
	
	private final ObservableCustomBlock base;
	private Block block = null;
	
	private static final String KEY_LOCATION = "location";
	
	/**
	 * @param world where the block be blacked
	 * @param base the spout custom block
	 */
	public WorldUniqueBlock(final World world, final ObservableCustomBlock base) {
		this.world = world;
		this.base = base;
	}
	
	@Override
	public void onBlockEvent(final BlockEvent blockEvent) {
		switch (blockEvent.getBlockEventType()) {
		case PLAYER_BLOCK_PLACED:

			if (block == null) {
				block = blockEvent.getBlock();
			} else if( blockEvent.getEntity() instanceof Player) {
				((Player) blockEvent.getEntity() ).sendMessage("there can be only one block of this type");
			} else {
				Bukkit.broadcastMessage("cannot place a block: " + base.getName());
			}
			break;
		
		case BLOCK_DESTROYED:
			block = null;
		break;

		default:
			break;
		}
		
	}

	@Override
	public void setup(final PersistenceMap values, final World world) throws PersistenceException {
		
		// bad style ... meh
		if(values.getMap() != null && values.getMap().containsKey(KEY_LOCATION)) {
			block = values.getBlock(KEY_LOCATION, world);
			SpoutManager.getMaterialManager().overrideBlock(block, base);
		}
		
				
		base.addObserver(this, world);
	}

	@Override
	public PersistenceMap dump() {
		PersistenceMap map = new PersistenceMap();
		
		if(block != null) {
			map.put(KEY_LOCATION, block);
		}
		
		return map;
	}

	@Override
	public void dispose() {
		
		if(block != null) {
			block.setType(Material.AIR);
		}
		base.removeObserver(this, world);
		
	}

	@Override
	public Set<Block> getBlocks() {
		
		Set<Block> blocks = newHashSet();
		
		if (block != null) {
		
			blocks.add(block);
			return blocks;
		}
		return blocks;
	}

}
