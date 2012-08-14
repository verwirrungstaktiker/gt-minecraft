package gt.plugin.helloworld;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

public class LevelSecurity implements Listener {
	
	/**
	 * Damaging Blocks should not be possible in the game
	 * NOTE: this doesn't prevent breaking blocks in creative mode
	 * @param event Damaging a block
	 */
	@EventHandler
	public void preventBlockDamage(final BlockDamageEvent event) {
		event.setCancelled(true);
	}
}

