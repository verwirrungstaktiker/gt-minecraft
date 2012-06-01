package gt.general.world;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Listener;



public abstract class CustomWorld {
	
	private World world;
	
	public World getWorld() {
		return world;
	}

	public abstract Location getSpawn();
	
}
