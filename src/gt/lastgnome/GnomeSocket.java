package gt.lastgnome;

import gt.plugin.helloworld.HelloWorld;

import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.block.design.*;
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;

/**
 * Item-class for GnomeSocket
 */
public class GnomeSocket extends GenericCubeCustomBlock {
	/**
	 * Creates a new GnomeSocket
	 * @param plugin  the plugin we run
	 * @param name the name of the gnomeSocket
	 * @param texture texture for the gnomeSocket
	 */
	public GnomeSocket(final Plugin plugin, final String name, final String texture) {
		// 91 is blockID of overwritten block
		// 16 is texture size
		// last parameter is "rotatetility"
		super(plugin, name, 91, texture, 16, true);
	}

	/**
	 * Creates a new GnomeSocket
	 * @param plugin the plugin we run
	 */
	public GnomeSocket(final Plugin plugin) {
		this(plugin, "GnomeSocket", "http://dl.dropbox.com/u/29386658/gt/textures/gnome_socket_16x16.png");
		
	}

	/**
	 * Creates a new GnomeSocket
	 */
	public GnomeSocket() {
		this(HelloWorld.getPlugin());
	}

}
