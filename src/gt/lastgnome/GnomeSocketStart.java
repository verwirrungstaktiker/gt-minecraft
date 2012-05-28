package gt.lastgnome;

import gt.plugin.helloworld.HelloWorld;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.getspout.commons.entity.Player;
import org.getspout.spoutapi.block.design.*;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.block.GenericCubeCustomBlock;
import org.getspout.spoutapi.player.SpoutPlayer;

/**
 * Item-class for GnomeSocket
 */
public class GnomeSocketStart extends GenericCubeCustomBlock {
	/**
	 * Creates a new GnomeSocket
	 * @param plugin  the plugin we run
	 * @param name the name of the gnomeSocket
	 * @param texture texture for the gnomeSocket
	 */
	public GnomeSocketStart(final Plugin plugin, final String name, final String texture) {
		// 91 is blockID of overwritten block
		// 16 is texture size
		// last parameter is "rotatetility"
		super(plugin, name, 91, texture, 16, true);
	}

	/**
	 * Creates a new GnomeSocket
	 * @param plugin the plugin we run
	 */
	public GnomeSocketStart(final Plugin plugin) {
		this(plugin, "GnomeSocketStart", "http://dl.dropbox.com/u/29386658/gt/textures/gnome_socket_start_16x16.png");
		
	}

	/**
	 * Creates a new GnomeSocket
	 */
	public GnomeSocketStart() {
		this(HelloWorld.getPlugin());
	}
	
	@Override
	public boolean onBlockInteract(World world, int x, int y, int z, SpoutPlayer player) {
		ItemStack items = new SpoutItemStack(HelloWorld.gnome);
		player.getInventory().addItem(items);
		player.sendMessage(ChatColor.GREEN + "You obtained a Gnome for Testing");
		return true;
	}
	

}
