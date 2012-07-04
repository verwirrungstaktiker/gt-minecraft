package gt.plugin.helloworld.command;

import gt.general.GameManager;
import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.general.character.Team;
import gt.general.character.TeamManager;
import gt.lastgnome.game.LastGnomeGameBuilder;
import gt.plugin.meta.PlayerCommandExecutor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

/**
 * Handles the <tt>/gg</tt> command.
 * Starts a new LastGnomeGame for the Team of the sender.
 */
public class StartGameCommandExecutor extends PlayerCommandExecutor {

	private TeamManager teamManager;
	private GameManager gameManager;

	public StartGameCommandExecutor(final TeamManager teamManager, final GameManager gameManager) {
		this.teamManager = teamManager;
		this.gameManager = gameManager;
	}
	
	@Override
	public boolean onPlayerCommand(final Player player, final Command cmd, final String label, final String[] args) {

		Hero starter = HeroManager.getHero(player);
		
		// build the team
		Team team = starter.getTeam();
		if(team == Team.NOTEAM) {
			team = teamManager.initiateTeam(starter);
			
			for(Hero hero : HeroManager.getAllHeros()) {
				try {
					teamManager.addHeroToTeam(team, hero);
				} catch (TeamManager.TeamJoinException tje) {
					player.sendMessage(tje.getMessage());
				}
			}
		}
		
		Bukkit
			.getServer()
			.broadcastMessage("starting gnome game: " + player.getName());
		
		
		gameManager.startGame(new LastGnomeGameBuilder(team), "lastgnome");
		
		return true;
	}

}
