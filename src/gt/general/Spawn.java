package gt.general;

import static com.google.common.collect.Sets.newHashSet;
import gt.general.RespawnManager.RespawnPoint;
import gt.general.character.Hero;
import gt.general.character.Team;
import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.persistence.YamlSerializable;
import gt.general.logic.persistence.exceptions.PersistenceException;
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
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;

public class Spawn extends YamlSerializable implements BlockObserver, RespawnPoint {
	
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
	public void setup(final PersistenceMap values, final World world) throws PersistenceException {	
		ObservableCustomBlock spawnBlock = CustomBlockType.SPAWN_BLOCK.getCustomBlock();
		
		for(Block block : values.getBlocks(KEY_SPAWN, world)) {	
			SpoutManager.getMaterialManager().overrideBlock(block, spawnBlock);
			
			spawnBlocks.add(block);
		}
		
		spawnBlock.addObserver(this, world);
	}

	@Override
	public PersistenceMap dump() {	
		PersistenceMap ret = new PersistenceMap();
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
		
		switch (blockEvent.getBlockEventType()) {
		case PLAYER_BLOCK_PLACED:
			spawnBlocks.add(blockEvent.getBlock());
			break;
		case BLOCK_DESTROYED:
			spawnBlocks.remove(blockEvent.getBlock());

		default:
			break;
		}
	}

	/** spawn is not registered the default way! */
	@Override
	public void registerRespawnManager(final RespawnManager respawnManager) {}
	
	@Override
	public Location getRespawnLocation() {
		
		// why cant i get just and random element?
		Block block = (getBlocks().toArray(new Block[0]))[0];		
		return block.getLocation().add(0.5, 1.0, 0.5);
	}
}
