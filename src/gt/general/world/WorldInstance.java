package gt.general.world;

import org.bukkit.Location;
import org.bukkit.World;



public abstract class WorldInstance {
	
	private World world;
	private String name;
	
	public void setWorld(World world) {
		this.world = world;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public World getWorld() {
		return world;
	}
	
	public Location getSpawnLocation() {
		return world.getSpawnLocation();
	};
	
}
