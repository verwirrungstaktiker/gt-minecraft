package gt.general.world;


import gt.plugin.meta.CustomBlockType;
import gt.plugin.meta.Hello;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.block.design.GenericCubeBlockDesign;
import org.getspout.spoutapi.material.block.GenericCustomBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;


public class ObservableCustomBlock extends GenericCustomBlock {

	private CustomBlockType customBlockType;
	
	public enum BlockEventType {
		PLAYER_BLOCK_PLACED,
		BLOCK_DESTROYED,
		BLOCK_INTERACT,
		PLAYER_STEP_ON
	}
	
	// TODO this must be done better
	public class BlockEvent {		
		public Block block;
		public LivingEntity entity;
		public BlockEventType blockEventType;
		public Player player;
	}
	
	private Multimap<World, BlockObserver> observers = HashMultimap.create();

	/**
	 * Construct a new Custom block
	 * @param name			name of the new block
	 * @param textureUrl	texture of the new block
	 * @param textureSize	texture size of the new block
	 */
	public ObservableCustomBlock(final String name, final String textureUrl, final int textureSize){
		//TODO remove 1 (underlying block) if bug in spout is fixed
		super(Hello.getPlugin(), name, 1, false);
		
		GenericCubeBlockDesign design = new GenericCubeBlockDesign(Hello.getPlugin(), textureUrl, textureSize);
		design.setRenderPass(1);
		//TODO set these when updated to new spoutversion (old version has a bug here)
		setBlockDesign(design);
		
		setOpaque(false);
	}

	/**
	 * Fire a BlockPlayedEvent if someone places the block
	 * @param world world where the block was placed
	 * @param x		x-coordinate of the placed block
	 * @param y 	y-coordinate of the placed block
	 * @param z		z-coordinate of the placed block
	 * @param living	entity that placed the block
	 */
	public void onBlockPlace(final World world, final int x, final int y, final int z, final LivingEntity living) {
		BlockEvent e = new BlockEvent();
		e.block = world.getBlockAt(x, y, z);
		e.entity = living;
		e.blockEventType = BlockEventType.PLAYER_BLOCK_PLACED;
		
		fireBlockEvent(world, e);
	}
	
	/**
	 * Fire a BlockDestroyedEvent if someone breaks the block
	 * @param world	world where the block was broken
	 * @param x		x-coordinate of the block
	 * @param y		y-coordinate of the block
	 * @param z		z-coordinate of the block
	 */
	public void onBlockDestroyed(final World world, final int x, final int y, final int z) {
		
		BlockEvent e = new BlockEvent();
		e.block = world.getBlockAt(x, y, z);
		e.blockEventType = BlockEventType.BLOCK_DESTROYED;
		
		fireBlockEvent(world, e);
	}
	
	/**
	 * Fire a BlockInteractEvent if someone interacts with it
	 * @param world	world where the block stands
	 * @param x		x-coordinate of the block
	 * @param y		y-coordinate of the block
	 * @param z		z-coordinate of the block
	 * @param player	SpoutPlayer that interacted with the block
	 * @return always true
	 */
	public boolean onBlockInteract(final World world, final int x, final int y, final int z, final SpoutPlayer player) {
		
		BlockEvent e = new BlockEvent();
		e.block = world.getBlockAt(x, y, z);
		e.player = player;
		e.blockEventType = BlockEventType.BLOCK_INTERACT;
		
		fireBlockEvent(world, e);
	
		return true;
		
	}
	
	@Override
	public void onEntityMoveAt(World world, int x, int y, int z, Entity entity) {
		
		if(entity instanceof Player) {
			Player player = (Player) entity;
			
			BlockEvent e = new BlockEvent();
			e.block = world.getBlockAt(x, y, z);
			e.player = player;
			e.blockEventType = BlockEventType.PLAYER_STEP_ON;
			
			fireBlockEvent(world, e);
			
			System.out.println("player walked over custom block.");
		}
		
	}
	
	/**
	 * Fire a certain Event
	 * @param world			world that holds the block
	 * @param blockEvent	event to be fired
	 */
	private void fireBlockEvent(final World world, final BlockEvent blockEvent) {
		for(BlockObserver o : observers.get(world)) {
			o.onBlockEvent(blockEvent);
		}
	}
	
	/**
	 * @param blockObserver	Observer to be added
	 * @param world			world that holds the block
	 */
	public void addObserver(final BlockObserver blockObserver, final World world) {
		observers.put(world, blockObserver);
	}
	
	/**
	 * @param blockObserver	Observer to be deleted
	 * @param world			world that holds the block
	 */
	public void removeObserver(final BlockObserver blockObserver, final World world) {
		observers.remove(world, blockObserver);
	}

	/**
	 * @param customBlockType the type of the custom block
	 */
	public void setCustomBlockType(final CustomBlockType customBlockType) {
		this.customBlockType = customBlockType;
	}
	
	/**
	 * @return the type of this custom block
	 */
	public CustomBlockType getCustomBlockType() {
		return customBlockType;
	}
}
