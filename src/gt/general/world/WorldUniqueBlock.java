package gt.general.world;

import static com.google.common.collect.Sets.*;

import gt.general.trigger.persistance.YamlSerializable;
import gt.general.world.ObservableCustomBlock.BlockEvent;

import java.util.Map;
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
	
	public WorldUniqueBlock(final World world, final ObservableCustomBlock base) {
		this.world = world;
		this.base = base;
	}
	
	@Override
	public void onBlockEvent(final BlockEvent blockEvent) {
		switch (blockEvent.blockEventType) {
		case PLAYER_BLOCK_PLACED:

			if (block == null) {
				block = blockEvent.block;
			} else if( blockEvent.entity instanceof Player) {
				// TODO maybe more output
				((Player) blockEvent.entity ).sendMessage("there can be only one block of this type");
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
	public void setup(final Map<String, Object> values, final World world) {
		
		if(values != null) {
			block = blockFromCoordinates(values, world);
			SpoutManager.getMaterialManager().overrideBlock(block, base);
		}
		
				
		base.addObserver(this, world);
	}

	@Override
	public Map<String, Object> dump() {
		
		if(block != null) {
			return coordinatesFromBlock(block);
		}
		return null;
	}

	@Override
	public void dispose() {
		
		block.setType(Material.AIR);
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
