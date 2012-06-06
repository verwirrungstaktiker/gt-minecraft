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
		
		for(Hero member : team.getPlayers()) {
			
			member.getGui().removeGuiElement(GuiElementType.TEAMFRAME);
			
			team.removeHero(member);
			member.setTeam(null);
		}
		
		teams.remove(team);
	}

	/**
	 * @param team The team to be extended.
	 * @param hero The hero to be added to the Team.
	 */
	public void addHeroToTeam(final Team team, final Hero hero) {
		
		/*
		 * TODO constraints - already in a team / max four players
		 */
		
		team.addHero(hero);
		hero.setTeam(team);
		
		TeamFrame teamFrame = new TeamFrame(team);
		hero.getGui().addGuiElement(GuiElementType.TEAMFRAME, teamFrame);
		
	}	
}
