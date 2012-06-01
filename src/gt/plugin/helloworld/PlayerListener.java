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

public class PlayerListener implements Listener {

	/** is this event even doing anything? **/
	@EventHandler
	public final void preventSprinting(final PlayerToggleSprintEvent event) {
		event.setCancelled(true);
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
		if (event.getPlayer().getInventory().contains(eventItem)) {
			event.setCancelled(true);
		}
	}
}
