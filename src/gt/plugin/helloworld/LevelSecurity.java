/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation für kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Neß (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.plugin.helloworld;

import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

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
	
	/**
	 *  Handles events, when Zombie hits
	 * @param event an EntityDamageByEntity Event
	 */
	@EventHandler
	public void zombieDamage(final EntityDamageByEntityEvent event) {
		//On hit, Zombie drains 8 half hearts
		if (event.getDamager() instanceof Zombie) {
			event.setDamage(8);
		}
	}
}

