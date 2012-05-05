package gt.plugin.helloworld;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;



public class BlockListener implements Listener {
	
	@EventHandler
	public void stopDiamondBlockBreak(BlockBreakEvent event) {
		if (event.getBlock().getType() == Material.DIAMOND_BLOCK) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "Stop grieving!");
		}
	}

}
