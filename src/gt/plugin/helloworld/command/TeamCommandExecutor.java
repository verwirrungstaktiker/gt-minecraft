/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.plugin.helloworld.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.general.character.Team;
import gt.general.character.TeamManager;
import gt.plugin.meta.PlayerCommandExecutor;
/**
 * Handles the <tt>/team</tt> command.
 * /team disband Deletes the team of the sender
 * /team [nick] ... Creates a new team and invites the attached Heros
 */
public class TeamCommandExecutor extends PlayerCommandExecutor {

	private TeamManager teamManager;

	/**
	 * @param teamManager the running team manager
	 */
	public TeamCommandExecutor(final TeamManager teamManager) {
		this.teamManager = teamManager;
	}
	
	@Override
	public boolean onPlayerCommand(final Player player, final Command cmd, final String label, final String[] args) {
		Hero invoker = HeroManager.getHero(player);
		
		// disband the team
		if (args.length == 1 && args[0].equalsIgnoreCase("disband")) {
			Team team = invoker.getTeam();

			if(team==Team.NOTEAM) {
				player.sendMessage(ChatColor.RED + "There is no team to disband.");
			} else if(team.isFixed()) {
				player.sendMessage(ChatColor.RED + "Team is fix and can't be disbanded.");
			} else {
				System.out.println("disbanded a team");
				teamManager.disband(invoker.getTeam());
				return true;
			} 
		}
		
		// create a new team
		if (invoker.getTeam() == Team.NOTEAM) {
			teamManager.initiateTeam(invoker);
		}
		
		// now the invoker must have a team
		Team team = invoker.getTeam();
		for (String name : args) {
			if(!name.equalsIgnoreCase("disband")) {
				try {
					teamManager.addHeroToTeamByName(team, name);
				} catch (TeamManager.TeamJoinException tje) {
					player.sendMessage(tje.getMessage());
				}
			}
		}
		
		return true;
	}

}
