package gt.plugin.helloworld;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Listener;



public abstract class CustomWorld implements Listener {
	
	private World world;
	
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public abstract Location getSpawn();
	
}
