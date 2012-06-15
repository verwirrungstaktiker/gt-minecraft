package gt.general.world;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;
import org.getspout.spoutapi.material.item.GenericCustomItem;

/**
 * Represents one Instance of a World including metadata.
 * 
 * @author Sebastian Fahnenschreiber
 *
 */
public abstract class WorldInstance {
	
	private World world;
	private String name;
	
	/**
	 * @param world the minecraft representation of this world
	 */
	public void setWorld(final World world) {
		this.world = world;
		placeCustomBlocks();
	}
	
	/**
	 * @return the minecraft representation of this world
	 */
	public World getWorld() {
		return world;
	}
	
	/**
	 * @param name the name of this instance
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the name of this instance
	 */
	public String getName() {
		return name;
	}

	/**
	 * disposes this WorldInstance
	 */
	public void dispose() {
		world = null;
	}
	
	
	/**
	 * shortcut
	 * 
	 * @return where the players spawn
	 */
	public Location getSpawnLocation() {
		return world.getSpawnLocation();
	};
	
	
	//XXX: Testing
	/**
	 * places start socket & end socket
	 */
	private void placeCustomBlocks() {
		/**
		Location spawn = world.getSpawnLocation();
		spawnCustomBlockAtRelativeLocation(HelloWorld.gnomeSocketStart, spawn, -2, -2);
		
		spawnCustomBlockAtRelativeLocation(HelloWorld.gnomeSocketEnd, spawn, 2, 2);
		
		spawnCustomToolsAtRelativeLocation(HelloWorld.placeholderTool, 1, spawn, 2, -2);
		*/
		
	}
	
	protected void spawnCustomBlockAtRelativeLocation(final GenericCubeCustomBlock customBlock, final Location start,
														final int east, final int north) {
		
		Location loc = getRelativeLocation(start, east, north);
		
		Block oldBlock = world.getHighestBlockAt(loc);
		
		SpoutManager.getMaterialManager().overrideBlock(oldBlock, customBlock);
	}
	
	protected void spawnCustomToolsAtRelativeLocation(final GenericCustomItem customItem, final int amount, final Location start,
			final int east, final int north) {

		Location loc = getRelativeLocation(start, east, north);
		
		SpoutItemStack item = new SpoutItemStack(customItem, amount);
		
		world.dropItem(loc, item);
	}
	
	protected Location getRelativeLocation(final Location start, final int east, final int north) {
		return world.getBlockAt(start)
				.getRelative(BlockFace.EAST, east)
				.getRelative(BlockFace.NORTH, north)
				.getLocation();
	}

}
