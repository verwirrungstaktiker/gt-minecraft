/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.lastgnome;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import gt.general.logic.persistence.PersistenceMap;
import gt.general.logic.trigger.Trigger;
import gt.lastgnome.game.LastGnomeGame;


/**
 * triggers when the gnome is caught
 */
public class TeamLostTrigger extends Trigger implements Listener{
	/** the observed game*/
	private LastGnomeGame game;

	/**
	 * register death of gnome bearer
	 * @param event player plays game ... dies.
	 */
	@EventHandler
	public void heroDeath(final PlayerDeathEvent event) {
		Player gnomebearer = game.getGnomeBearer().getPlayer();
		
		if (gnomebearer.equals(event.getEntity())) {
			getContext().updateTriggerState(this, true, event.getEntity());
		}
	}
	
	/**
	 * @param game LastGnomeGame
	 */
	public void setGame(final LastGnomeGame game) {
		this.game = game;		
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public PersistenceMap dump() {
		
		return null;
	}

	@Override
	public void setup(final PersistenceMap values, final World world) {
		
	}

	@Override
	public Set<Block> getBlocks() {
		return new HashSet<Block>();
	}
}
