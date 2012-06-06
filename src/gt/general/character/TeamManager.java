package gt.general.character;

import gt.general.gui.GuiElementType;
import gt.general.gui.TeamFrame;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Sebastian Fahnenschreiber
 *
 */
public class TeamManager {

	private final Set<Team> teams;

	public class TeamJoinException extends RuntimeException {
		/** */
		private static final long serialVersionUID = -1343429439804370656L;
		
		/**
		 * @param msg the reason the Hero cant join the Team.
		 */
		public TeamJoinException(final String msg) {
			super(msg);
		}
		
	}
	
	/**
	 * Generates a new TeamManager.
	 */
	public TeamManager() {
		teams = new HashSet<Team>();
	}
	
	/**
	 * @param initiator The initial member of the team.
	 * @return The freshly initiated Team.
	 */
	public Team initiateTeam(final Hero initiator) {
		Team team = new Team();
		addHeroToTeam(team, initiator);
		teams.add(team);
		
		return team;
	}
	
	/**
	 * @param team The team to be disbanded.
	 */
	public void disband(final Team team) {
				
		for(Hero hero : team.getPlayers()) {
			hero.getGui().removeGuiElement(GuiElementType.TEAMFRAME);
		}
		
		team.dispose();
		teams.remove(team);
	}

	/**
	 * @param team The Team to be extended.
	 * @param hero The Hero to be added to the Team.
	 */
	public void addHeroToTeam(final Team team, final Hero hero) {
		
		if( hero.getTeam() != Team.NOTEAM) {
			throw new TeamJoinException("already in a team: " + hero.getPlayer().getName());
		}
		
		if( team.getPlayers().size() >= 4 ) {
			throw new TeamJoinException("team is full, player cant join: " + hero.getPlayer().getName());
		}
		
		team.addHero(hero);
		hero.setTeam(team);
		
		TeamFrame teamFrame = new TeamFrame(team);
		hero.getGui().addGuiElement(GuiElementType.TEAMFRAME, teamFrame);		
	}

	/**
	 * @param team The Team to be extended.
	 * @param name The name of the Hero to be added to the Team.
	 */
	public void addHeroToTeamByName(final Team team, final String name) {
		Hero hero = HeroManager.getHero(name);
	
		if( hero == null ) {
			throw new TeamJoinException("no such player: " + name);
		}
		
		addHeroToTeam(team, hero);
	}	
}
