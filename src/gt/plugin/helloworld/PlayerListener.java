package gt.plugin.helloworld;

import gt.general.PortableItem;
import gt.lastgnome.GnomeItem;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.Material;

public class PlayerListener implements Listener {
	
	/** 
	 * prevent sprinting 
	 * @param event player toggles sprint
	 **/
//	@EventHandler
//	public final void preventSprinting(final PlayerToggleSprintEvent event) {
//		SpoutPlayer player = (SpoutPlayer) event.getPlayer();
//		// cancelling the event doesn't do anything, so have this dirty hack instead
//		if (event.isSprinting()) {
//			player.setWalkingMultiplier(player.getWalkingMultiplier()*0.66);
//		} else {
//			player.setWalkingMultiplier(player.getWalkingMultiplier()*1.5);
//		}
//
//		event.setCancelled(true);
//	}
	
	@EventHandler
	public final void preventInventoryModification(final InventoryClickEvent event) {
		HumanEntity human = event.getWhoClicked();
		if (human instanceof Player) {
			Player player = (Player) human;
			
			if(player.getGameMode() == GameMode.SURVIVAL) {
				event.setCancelled(true);
			}
		}
	}

	/** 
	 * prevents item dropping for tools
	 * @param event player drops an item
	 **/
	@EventHandler
	public final void preventGnomeDropping(final PlayerDropItemEvent event) {
		Material mat = new SpoutItemStack(event.getItemDrop().getItemStack()).getMaterial();
		
		if (mat instanceof PortableItem) {
			PortableItem portable = (PortableItem) mat;
			
			if (!portable.isDropable()) {
				event.setCancelled(true);
				event.getPlayer().sendMessage(ChatColor.RED + "This item cannot be dropped");
			}
		}
		
	}

	/** 
	 * no hunger during games
	 * @param event foodlevel changes for a player
	 **/
	@EventHandler
	public final void noHunger(final FoodLevelChangeEvent event) {
		event.setFoodLevel(20);
		event.setCancelled(true);		
	}
	
	/** 
	 * prevents carrying more than one item of the same kind 
	 * @param event player picks up an item
	 * 
	 * XXX: buggy if 2+ items picked up at same time
	 */
	@EventHandler
	public final void carryOnlyOneOfEachKind(final PlayerPickupItemEvent event) {
		ItemStack eventItem = event.getItem().getItemStack();
		PlayerInventory inv = event.getPlayer().getInventory();
				
		boolean itemAlreadyCarried = inv.contains(eventItem);
		boolean gnomeCarried =  inv.contains(GnomeItem.RAWID);
		boolean twoItemsCarried = inventoryContainsTwoPlusStacks(inv);
		if (itemAlreadyCarried || gnomeCarried || twoItemsCarried) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public final void preventKicking(final PlayerKickEvent event) {
		if(event.getReason().contains("quickly")) {
		    event.setLeaveMessage("");
		    event.setCancelled(true);
		}
	}
	
	/**
	 * quick & dirty
	 * @return true if inventory contains > 1 stacks
	 * @param inv the player's inventory
	 */
	private boolean inventoryContainsTwoPlusStacks(final PlayerInventory inv) {
		ItemStack[] contents = inv.getContents();
		int itemcount = 0;
		for(int i=0; i<contents.length; i++) {
			if(contents[i] != null) {
				itemcount++;
			}
			if(itemcount > 1) {
				return true;
			}
		}
		return false;
	}
	
	
}
