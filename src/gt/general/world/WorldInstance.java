package gt.general.world;

import gt.plugin.helloworld.HelloWorld;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;

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
		Location spawn = world.getSpawnLocation();
		
		spawnCustomBlockAtRelativeLocation(HelloWorld.gnomeSocketStart, spawn, -2, -2);
		
		spawnCustomBlockAtRelativeLocation(HelloWorld.gnomeSocketEnd, spawn, 2, 2);
		
		
	}
	
	private void spawnCustomBlockAtRelativeLocation(final GenericCubeCustomBlock customBlock, final Location start,
														final int east, final int north) {
		Location loc = world.getBlockAt(start)
				.getRelative(BlockFace.EAST, east)
				.getRelative(BlockFace.NORTH, north)
				.getLocation();
		
		Block oldBlock = world.getHighestBlockAt(loc);
		
		SpoutManager.getMaterialManager().overrideBlock(oldBlock, customBlock);
	}

}
