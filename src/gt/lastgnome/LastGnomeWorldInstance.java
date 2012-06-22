package gt.lastgnome;

import gt.general.trigger.TriggerManager;
import gt.general.world.WorldInstance;

import org.bukkit.Location;
import org.bukkit.World;

public class LastGnomeWorldInstance extends WorldInstance {
	
	private GnomeSocketStart startSocket;
	private GnomeSocketEnd endSocket;
	
	/**
	 * @param world The Minecraft representation of the World.
	 */
	public LastGnomeWorldInstance(final World world, final TriggerManager triggerManager) {
		super(world, triggerManager);
	}
	
	public LastGnomeWorldInstance(final World world,
									final TriggerManager triggerManager, 
									final GnomeSocketStart start, 
									final GnomeSocketEnd end) {
		this(world, triggerManager);
		
		startSocket = start;
		endSocket = end;
		
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
