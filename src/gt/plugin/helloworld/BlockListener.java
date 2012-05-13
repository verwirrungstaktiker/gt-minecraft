package gt.plugin.helloworld;

import gt.lastgnome.GnomeItem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.Item;



public class BlockListener implements Listener {
	
	@EventHandler
	public void stopGoldBlockBreak(BlockBreakEvent event) {
		if (event.getBlock().getType() == Material.GOLD_BLOCK) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.GREEN + "Obtained a Gnome!");
			
			ItemStack items = new SpoutItemStack(HelloWorld.gnome, 1);
			event.getPlayer().getInventory().addItem(items);
		}
	}

}