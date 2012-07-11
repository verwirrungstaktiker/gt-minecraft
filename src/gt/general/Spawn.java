package gt.general;

import static com.google.common.collect.Sets.newHashSet;
import gt.general.character.Hero;
import gt.general.character.Team;
import gt.general.logic.persistance.PersistanceMap;
import gt.general.logic.persistance.YamlSerializable;
import gt.general.world.BlockObserver;
import gt.general.world.ObservableCustomBlock;
import gt.general.world.ObservableCustomBlock.BlockEvent;
import gt.plugin.meta.CustomBlockType;

import java.util.Iterator;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.getspout.spoutapi.SpoutManager;

public class Spawn extends YamlSerializable implements BlockObserver {
	
	public static final String KEY_SPAWN = "spawn";
	
	public static final String PERSISTANCE_FILE = "spawn.yml";
	
	private World world;
	private final Set<Block> spawnBlocks;
	
	/**
	 * creates a new spawn
	 */
	public Spawn() {
		spawnBlocks = newHashSet();
	}

	@Override
	public void setup(final PersistanceMap values, final World world) {	
		ObservableCustomBlock spawnBlock = CustomBlockType.SPAWN_BLOCK.getCustomBlock();
		
		for(Block block : values.getBlocks(KEY_SPAWN, world)) {	
			SpoutManager.getMaterialManager().overrideBlock(block, spawnBlock);
			
			spawnBlocks.add(block);
		}
		
		spawnBlock.addObserver(this, world);
	}

	@Override
	public PersistanceMap dump() {	
		PersistanceMap ret = new PersistanceMap();
		ret.put(KEY_SPAWN, getBlocks());
		
		return ret;
	}

	@Override
	public void dispose() {
		ObservableCustomBlock spawnBlock = CustomBlockType.SPAWN_BLOCK.getCustomBlock();
		spawnBlock.removeObserver(this, world);
		
		//workaround for concurrent modification exception
		Block[] blocks = {};
		blocks = getBlocks().toArray(blocks);
		for(Block block : blocks) {
			block.setType(Material.AIR);
		}
		
		spawnBlocks.clear();
		
		
	}

	@Override
	public Set<Block> getBlocks() {
		return spawnBlocks;
	}

	/**
	 * @param team the team to be spawned
	 */
	public void spawnTeam(final Team team) {
		Iterator<Block> blocks = spawnBlocks.iterator();
		Iterator<Hero> heros = team.getPlayers().iterator();
		
		while(blocks.hasNext() && heros.hasNext()) {
			Location location = blocks.next().getLocation();
			location.add(0.5, 0, 0.5); // center
			
			heros.next().getPlayer().teleport(location);
		}
	}

	@Override
	public void onBlockEvent(final BlockEvent blockEvent) {
		
		switch (blockEvent.blockEventType) {
		case PLAYER_BLOCK_PLACED:
			spawnBlocks.add(blockEvent.block);
			break;
		case BLOCK_DESTROYED:
			spawnBlocks.remove(blockEvent.block);

		default:
			break;
		}
	}
}
