package gt.plugin.helloworld;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.getspout.spoutapi.inventory.SpoutItemStack;



public class BlockListener implements Listener {
	
	/**
	 * FOR TESTING ONLY
	 * @param event
	 */
	@EventHandler
	public void clearInventoryOnGoldBlockDamage(BlockDamageEvent event) {
		if (event.getBlock().getType() == Material.GOLD_BLOCK) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.GREEN + "Cleaned up your inventory!");
			event.getPlayer().getInventory().clear();
		}
	}
	
	/**
	 * FOR TESTING ONLY
	 * @param event
	 */
	@EventHandler
	public void clearInventoryOnGoldBlockBreak(BlockBreakEvent event) {
		if (event.getBlock().getType() == Material.GOLD_BLOCK) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.GREEN + "Cleaned up your inventory!");
			event.getPlayer().getInventory().clear();
		}
	}
	

}