package gt.lastgnome;

import gt.plugin.helloworld.HelloWorld;

import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * Item-class for GnomeSocket
 */
public class GnomeSocketEnd extends GenericCubeCustomBlock {
	/**
	 * Creates a new GnomeSocket
	 * @param plugin  the plugin we run
	 * @param name the name of the gnomeSocket
	 * @param texture texture for the gnomeSocket
	 */
	public GnomeSocketEnd(final Plugin plugin, final String name, final String texture) {
		// 91 is blockID of overwritten block
		// 16 is texture size
		// last parameter is "rotatetility"
		super(plugin, name, 91, texture, 16, true);
	}

	/**
	 * Creates a new GnomeSocket
	 * @param plugin the plugin we run
	 */
	public GnomeSocketEnd(final Plugin plugin) {
		this(plugin, "GnomeSocketEnd", "http://dl.dropbox.com/u/29386658/gt/textures/gnome_socket_end_16x16.png");
		
	}

	/**
	 * Creates a new GnomeSocket
	 */
	public GnomeSocketEnd() {
		this(HelloWorld.getPlugin());
		
	}
	
	@Override
	public boolean onBlockInteract(final World world, final int x, final int y, final int z, final SpoutPlayer player) {
		if(player.getInventory().contains(GnomeItem.RAWID)) {
			player.sendMessage("The Gnome has been saved");
			player.getInventory().remove(GnomeItem.RAWID);
			//TODO: some extern method could be called here
		} else {
			player.sendMessage("The mighty Gnome Socket demands the Gnome!");
		}
		return true;
	}
	

}
