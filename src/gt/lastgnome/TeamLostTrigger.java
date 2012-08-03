package gt.lastgnome;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import gt.general.logic.persistance.PersistanceMap;
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
	public PersistanceMap dump() {
		
		return null;
	}

	@Override
	public void setup(final PersistanceMap values, final World world) {
		
	}

	@Override
	public Set<Block> getBlocks() {
		return new HashSet<Block>();
	}
}
