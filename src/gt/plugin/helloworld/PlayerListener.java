package gt.plugin.helloworld;

import gt.lastgnome.GnomeItem;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.getspout.spoutapi.player.SpoutPlayer;

public class PlayerListener implements Listener {

	/** prevent sprinting **/
	@EventHandler
	public final void preventSprinting(final PlayerToggleSprintEvent event) {
		SpoutPlayer player = (SpoutPlayer) event.getPlayer();
		// cancelling the event doesn't do anything, so have this dirty hack instead
		if (event.isSprinting()) {
			player.setWalkingMultiplier(0.66);
		} else {
			player.setWalkingMultiplier(1.0);
		}
	}

	/** prevents item dropping for Gnomes only. **/
	@EventHandler
	public final void preventGnomeDropping(final PlayerDropItemEvent event) {
		if (event.getItemDrop().getItemStack().getTypeId() == GnomeItem.RAWID) {
			event.setCancelled(true);
			event.getPlayer().sendMessage(ChatColor.RED + "The Gnome is honorbound & can't be dropped");
		}
		
	}

	/** prevents carrying more than one item of the same kind **/
	@EventHandler
	public final void carryOnlyOneOfEachKind(final PlayerPickupItemEvent event) {
		//TODO: Carry max 2 items
		ItemStack eventItem = event.getItem().getItemStack();
		PlayerInventory inv = event.getPlayer().getInventory();
				
		boolean itemAlreadyCarried = inv.contains(eventItem);
		boolean gnomeCarried =  inv.contains(GnomeItem.RAWID);
		if (itemAlreadyCarried || gnomeCarried) {
			event.setCancelled(true);
		}
	}
}
