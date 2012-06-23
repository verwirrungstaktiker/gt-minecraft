package gt.lastgnome;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import gt.general.trigger.Trigger;


/**
 * triggers when the gnome is caught
 */
public class TeamLostTrigger extends Trigger implements Listener{
	/** the observed game*/
	private LastGnomeGame game;

	@EventHandler
	public void heroDeath(PlayerDeathEvent event) {
		Player gnomebearer = game.getGnomeBearer().getPlayer();
		
		if (gnomebearer.equals(event.getEntity())) {
			getContext().updateTriggerState(this, true);
		}
	}
	
	public void setGame(LastGnomeGame game) {
		this.game = game;		
	}
	
	@Override
	public void dispose() {
		
	}

	@Override
	public Map<String, Object> dump() {
		
		return null;
	}

	@Override
	public void setup(Map<String, Object> values, World world) {
		
	}

	@Override
	public Set<Block> getBlocks() {
		return new HashSet<Block>();
	}



}
