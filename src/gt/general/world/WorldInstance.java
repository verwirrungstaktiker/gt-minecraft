package gt.general.world;

import org.bukkit.Location;
import org.bukkit.World;

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
	
}
