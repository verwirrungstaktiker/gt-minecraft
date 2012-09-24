/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation f�r kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ne� (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.world;


import gt.plugin.meta.CustomBlockType;
import gt.plugin.meta.Hello;

import org.bukkit.Bukkit;
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
	
	public class BlockEvent {		
		private Block block;
		private LivingEntity entity;
		private BlockEventType blockEventType;
		private Player player;
		/**
		 * @return the block
		 */
		public Block getBlock() {
			if(block == null) {
				printWarning("block");
			}
			
			return block;
		}
		/**
		 * @param block the block to set
		 */
		public void setBlock(final Block block) {
			this.block = block;
		}
		/**
		 * @return the entity
		 */
		public LivingEntity getEntity() {
			if(entity == null) {
				printWarning("entity");
			}
			
			return entity;
		}
		/**
		 * @param entity the entity to set
		 */
		public void setEntity(final LivingEntity entity) {
			this.entity = entity;
		}
		/**
		 * @return the blockEventType
		 */
		public BlockEventType getBlockEventType() {
			return blockEventType;
		}
		/**
		 * @param blockEventType the blockEventType to set
		 */
		public void setBlockEventType(final BlockEventType blockEventType) {
			this.blockEventType = blockEventType;
		}
		/**
		 * @return the player
		 */
		public Player getPlayer() {
			if(player == null) {
				printWarning("player");
			}
			return player;
		}
		/**
		 * @param player the player to set
		 */
		public void setPlayer(final Player player) {
			this.player = player;
		}
		
		/**
		 * print a warning message for bukkit
		 * @param field warning message
		 */
		private void printWarning(final String field) {			
			String message = "BlockEvent: field is null :" + field + "\n" +
								"--type: " + blockEventType.toString() + "\n";

			Bukkit.getLogger().warning(message);			
		}
	}
	
	private Multimap<World, BlockObserver> observers = HashMultimap.create();

	/**
	 * Construct a new Custom block
	 * @param name			name of the new block
	 * @param textureUrl	texture of the new block
	 * @param textureSize	texture size of the new block
	 */
	public ObservableCustomBlock(final String name, final String textureUrl, final int textureSize){
		super(Hello.getPlugin(), name, 1, false);
		
		GenericCubeBlockDesign design = new GenericCubeBlockDesign(Hello.getPlugin(), textureUrl, textureSize);
		design.setRenderPass(1);
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
		e.setBlock(world.getBlockAt(x, y, z));
		e.setEntity(living);
		e.setBlockEventType(BlockEventType.PLAYER_BLOCK_PLACED);
		
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
		e.setBlock(world.getBlockAt(x, y, z));
		e.setBlockEventType(BlockEventType.BLOCK_DESTROYED);
		
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
		e.setBlock(world.getBlockAt(x, y, z));
		e.setPlayer(player);
		e.setBlockEventType(BlockEventType.BLOCK_INTERACT);
		
		fireBlockEvent(world, e);
	
		return true;
		
	}
	
	@Override
	public void onEntityMoveAt(final World world, final int x, final int y, final int z, final Entity entity) {
		
		if(entity instanceof Player) {
			Player player = (Player) entity;
			
			BlockEvent e = new BlockEvent();
			e.setBlock(world.getBlockAt(x, y, z));
			e.setPlayer(player);
			e.setBlockEventType(BlockEventType.PLAYER_STEP_ON);
			
			fireBlockEvent(world, e);
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
