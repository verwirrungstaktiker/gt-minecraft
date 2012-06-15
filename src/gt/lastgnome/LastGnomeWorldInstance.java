package gt.lastgnome;

import org.bukkit.Location;
import org.bukkit.World;

import gt.general.world.WorldInstance;

public class LastGnomeWorldInstance extends WorldInstance {
	
	private final GnomeSocketStart startSocket;
	private final GnomeSocketEnd endSocket;
	
	public LastGnomeWorldInstance(World world) {
		super();
		setWorld(world);
		
		startSocket = new GnomeSocketStart();
		endSocket = new GnomeSocketEnd();
		
		placeBlocks();
	}
	
	private void placeBlocks() {
		Location spawn = getWorld().getSpawnLocation();
		spawnCustomBlockAtRelativeLocation(startSocket, spawn, -2, -2);
		spawnCustomBlockAtRelativeLocation(endSocket, spawn, 2, 2);
	}
	
	
	public GnomeSocketStart getStartSocket() {
		return startSocket;
	}
	
	public GnomeSocketEnd getEndSocket() {
		return endSocket;
	}
	
}
