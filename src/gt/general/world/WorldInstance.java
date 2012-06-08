package gt.general.world;

import gt.plugin.helloworld.HelloWorld;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.getspout.spoutapi.SpoutManager;

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
	public void placeCustomBlocks() {
		Location spawn = world.getSpawnLocation();
		SpoutManager.getMaterialManager().overrideBlock(world.getBlockAt(spawn), HelloWorld.gnomeSocketStart);
		
		Location offsetSpawn = world.getBlockAt(spawn)
				.getRelative(BlockFace.NORTH, 3)
				.getRelative(BlockFace.EAST, 3)
				.getLocation();
		Block block = world.getHighestBlockAt(offsetSpawn);
		//place a end-socket 10 diagonal locks away
		SpoutManager.getMaterialManager().overrideBlock(block, HelloWorld.gnomeSocketEnd);
	}

}
