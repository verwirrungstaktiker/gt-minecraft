package gt.general.world;


import gt.plugin.meta.Hello;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;


public class ObservableCustomBlock extends GenericCubeCustomBlock {

	public enum BlockEventType {
		PLAYER_BLOCK_PLACED,
		BLOCK_PLACED,
		BLOCK_DESTROYED,
		BLOCK_INTERACT
	}
	
	public class BlockEvent {		
		public BlockEvent(Block blockAt, LivingEntity living,
				BlockEventType type) {
			block = blockAt;
			entity = living;
			blockEventType = type;
		}
		public final Block block;
		public final LivingEntity entity;
		public final BlockEventType blockEventType;
	}
	
	Multimap<World, BlockObserver> observers = HashMultimap.create();
	
	public ObservableCustomBlock(String name, String textureUrl, int textureSize){
		super(Hello.getPlugin(), name, textureUrl, textureSize);
	}

	public void onBlockPlace(World world, int x, int y, int z, final LivingEntity living) {
		
		fireBlockEvent(world,
						new BlockEvent (world.getBlockAt(x, y, z),
									 	living,
									 	BlockEventType.PLAYER_BLOCK_PLACED));
	}
	
	public void onBlockDestroyed(World world, int x, int y, int z) {
		fireBlockEvent(world,
				new BlockEvent (world.getBlockAt(x, y, z),
							 	null,
							 	BlockEventType.BLOCK_DESTROYED));
	}
	
	
	public boolean onBlockInteract(World world, int x, int y, int z, SpoutPlayer player) {
		fireBlockEvent(world,
				new BlockEvent (world.getBlockAt(x, y, z),
							 	player,
							 	BlockEventType.BLOCK_INTERACT));
	
		return true;
		
	}
	
	private void fireBlockEvent(final World world, final BlockEvent blockEvent) {
		for(BlockObserver o : observers.get(world)) {
			o.onBlockEvent(blockEvent);
		}
	}
	
	public void addObserver(final BlockObserver blockObserver, final World world) {
		observers.put(world, blockObserver);
	}
	
	public void removeObserver(final BlockObserver blockObserver, final World world) {
		observers.remove(world, blockObserver);
	}
}
