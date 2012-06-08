package gt.plugin.helloworld;

import gt.general.PortableItem;
import gt.lastgnome.GnomeItem;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.material.Material;
import org.getspout.spoutapi.player.SpoutPlayer;

public class PlayerListener implements Listener {
	
	/** 
	 * prevent sprinting 
	 * @param event player toggles sprint
	 **/
	@EventHandler
	public final void preventSprinting(final PlayerToggleSprintEvent event) {
		SpoutPlayer player = (SpoutPlayer) event.getPlayer();
		// cancelling the event doesn't do anything, so have this dirty hack instead
		if (event.isSprinting()) {
			player.setWalkingMultiplier(player.getWalkingMultiplier()*0.66);
		} else {
			player.setWalkingMultiplier(player.getWalkingMultiplier()*1.5);
		}
	}

	/** 
	 * prevents item dropping for Gnomes only.
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
