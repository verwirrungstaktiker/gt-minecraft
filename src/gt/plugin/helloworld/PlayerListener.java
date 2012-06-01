package gt.plugin.helloworld;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener{

	/** is this event even working? **/
	@EventHandler
	public void noSprinting(final PlayerToggleSprintEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void carryOnlyOneOfEachKind(final PlayerPickupItemEvent event) {
		//TODO: implement
		ItemStack eventItem = event.getItem().getItemStack();
		if(event.getPlayer().getInventory().contains(eventItem)) {
			event.setCancelled(true);
		}
	}
}
