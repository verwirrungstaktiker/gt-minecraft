package gt.lastgnome;

import gt.general.trigger.Trigger;
import gt.general.trigger.TriggerManager;

import org.bukkit.ChatColor;
import org.bukkit.Server;

public class TeamLostTrigger extends Trigger{

	protected LastGnomeTeam team;
	protected TriggerManager tm;
	
	public TeamLostTrigger (LastGnomeTeam team, Runnable callback, TriggerManager tm) {
		super(false, callback, tm);
		this.team = team;
	}
	
	@Override
	public void checkTrigger() {
		if (team.getGnomeBearer() == null) return;
		if (team.getGnomeBearer().getPlayer().getHealth() <= 0) {
			callback.run();
			tm.deregisterTrigger(this);
		}
				
	}

}
