package gt.lastgnome;

import gt.general.Spawn;
import gt.general.trigger.TriggerManager;
import gt.general.world.WorldInstance;
import gt.plugin.Hello;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.getspout.spoutapi.SpoutManager;

public class LastGnomeWorldInstance extends WorldInstance {
	
	private GnomeSocketStart startSocket;
	private GnomeSocketEnd endSocket;
	
	/**
	 * @param world The Minecraft representation of the World.
	 */
	public LastGnomeWorldInstance(final World world, final TriggerManager triggerManager, final Spawn spawn) {
		super(world, triggerManager, spawn);
	}
	
	public LastGnomeWorldInstance(final World world,
									final TriggerManager triggerManager, 
									final GnomeSocketStart start, 
									final GnomeSocketEnd end) {
		this(world, triggerManager, new Spawn(Hello.plugin)); // TODO this will crash
		
		startSocket = start;
		endSocket = end;
		
		System.out.println(world.getName());
		
		Block s = world.getBlockAt(-5, 66, 15);
		Block e = world.getBlockAt(86, 66, 15);
		
		System.out.println(SpoutManager.getMaterialManager().overrideBlock(s, start));
		System.out.println(SpoutManager.getMaterialManager().overrideBlock(e, end));
		
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
