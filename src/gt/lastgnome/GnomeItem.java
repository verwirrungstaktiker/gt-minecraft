package gt.lastgnome;

import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.material.item.GenericCustomItem;

public class GnomeItem extends GenericCustomItem {
	
	public GnomeItem(Plugin plugin, String name, String texture) {
		super(plugin, name, texture);
	}
	
	public GnomeItem(Plugin plugin) {
		super(plugin, "GnomeItem", "res/textures/gnome_16x16.png");
	}
}
