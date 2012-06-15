package gt.lastgnome;

import gt.general.world.WorldInstance;

import org.bukkit.Location;
import org.bukkit.World;

public class LastGnomeWorldInstance extends WorldInstance {
	
	private final GnomeSocketStart startSocket;
	private final GnomeSocketEnd endSocket;
	
	/**
	 * @param world The Minecraft representation of the World.
	 */
	public LastGnomeWorldInstance(final World world) {
		super();
		setWorld(world);
		
		startSocket = new GnomeSocketStart();
		endSocket = new GnomeSocketEnd();
		
		placeBlocks();
	}
	
	/**
	 * XXX Debug
	 */
	private void placeBlocks() {
		Location spawn = getWorld().getSpawnLocation();
		spawnCustomBlockAtRelativeLocation(startSocket, spawn, -2, -2);
		spawnCustomBlockAtRelativeLocation(endSocket, spawn, 2, 2);
	}
	
	/**
	 * @return the start socket of this world
	 */
	public GnomeSocketStart getStartSocket() {
		return startSocket;
	}
	
	/**
	 * @return the end socket of this world
	 */
	public GnomeSocketEnd getEndSocket() {
		return endSocket;
	}
	
}
