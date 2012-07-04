package gt.plugin.helloworld.command;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.general.character.Team;
import gt.general.character.TeamManager;
import gt.plugin.PlayerCommandExecutor;
/**
 * Handles the <tt>/team</tt> command.
 * /team disband Deletes the team of the sender
 * /team [nick] ... Creates a new team and invites the attached Heros
 */
public class TeamCommandExecutor extends PlayerCommandExecutor {

	private TeamManager teamManager;

	public TeamCommandExecutor(final TeamManager teamManager) {
		this.teamManager = teamManager;
	}
	
	@Override
	public boolean onPlayerCommand(final Player player, final Command cmd, final String label, final String[] args) {
		Hero invoker = HeroManager.getHero(player);
		
		// disband the team
		if (args.length == 1 && args[0].equalsIgnoreCase("disband")) {
			
			System.out.println("disbanded a team");
			teamManager.disband(invoker.getTeam());
			return true;
		}
		
		// create a new team
		if (invoker.getTeam() == Team.NOTEAM) {
			teamManager.initiateTeam(invoker);
		}
		
		// now the invoker must have a team
		Team team = invoker.getTeam();
		for (String name : args) {
			
			try {
				teamManager.addHeroToTeamByName(team, name);
			} catch (TeamManager.TeamJoinException tje) {
				player.sendMessage(tje.getMessage());
			}
			
		}
		
		// XXX DEBUG
		System.out.println(invoker.getTeam().toString());
		return true;
	}

}
