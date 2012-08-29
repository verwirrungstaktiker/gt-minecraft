package gt.plugin.helloworld.command;

import gt.general.GameManager;
import gt.general.character.Hero;
import gt.general.character.HeroManager;
import gt.general.character.Team;
import gt.general.character.TeamManager;
import gt.lastgnome.game.LastGnomeGameBuilder;
import gt.plugin.meta.PlayerCommandExecutor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

/**
 * Handles the <tt>/gg</tt> command.
 * Starts a new LastGnomeGame for the Team of the sender.
 */
public class StartGameCommandExecutor extends PlayerCommandExecutor {

	private TeamManager teamManager;
	private GameManager gameManager;

	/**
	 * @param teamManager the running team manager 
	 * @param gameManager the running game manager
	 */
	public StartGameCommandExecutor(final TeamManager teamManager, final GameManager gameManager) {
		this.teamManager = teamManager;
		this.gameManager = gameManager;
	}
	
	@Override
	public boolean onPlayerCommand(final Player player, final Command cmd, final String label, final String[] args) {

		Hero starter = HeroManager.getHero(player);
		
		boolean force = args.length > 0 && args[0].equalsIgnoreCase("force");
		
		if(starter.inGame() && !force ) {
			starter.getPlayer().sendMessage(ChatColor.RED + "you are already in a game!");
			return true;
		}
		
		// build the team
		Team team = starter.getTeam();
		if(team == Team.NOTEAM) {
			team = teamManager.initiateTeam(starter);
			
			for(Hero hero : HeroManager.getAllHeros()) {
				
				if(team.getPlayers().size() < 4) {
					try {
						teamManager.addHeroToTeam(team, hero);
					} catch (TeamManager.TeamJoinException tje) {
						player.sendMessage(tje.getMessage());
					}
				}
			}
		}
		
		if(team.getPlayers().size() > 4) {
			Bukkit
				.getServer()
				.broadcastMessage("starting gnome game: " + player.getName());
			
			
			gameManager.startGame(new LastGnomeGameBuilder(team), "lastgnome");
		} else {
			Bukkit
				.getServer()
				.broadcastMessage(ChatColor.RED + "team to big to start the gnome game (max. 4 players). use /team disband");
		}
		return true;
	}

}
