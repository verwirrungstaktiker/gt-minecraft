package gt.general.gui;

import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;

import gt.general.character.Hero;
import gt.general.character.Team;
import gt.general.character.TeamObserver;
import gt.general.character.Hero.Notification;

public class TeamFrame implements GuiElement, TeamObserver {

	private final Team team;
	
	
	private class MemberFrame {
		
		private final GenericGradient background;
		private final GenericLabel name;
		
		public MemberFrame (final String name) {
			this.background = new GenericGradient();
			this.name = new GenericLabel(name);
			
		}
		
	}
	
	public TeamFrame(final Team team) {
		this.team = team;
		
		team.addTeamObserver(this);
	}
	
	@Override
	public void attach(final Hero hero) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void detach(final Hero hero) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(final Hero hero, final Notification notification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Team team,
			gt.general.character.Team.Notification notification) {
		
		// TODO ensure, every Hero has a registered listener
		
	}
	
	
	

}
