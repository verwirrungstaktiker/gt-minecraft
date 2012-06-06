package gt.general.gui;

import gt.general.character.Hero;
import gt.general.character.Hero.Notification;
import gt.general.character.Team;
import gt.general.character.TeamObserver;

import org.getspout.spoutapi.gui.GenericLabel;

public class TeamFrame implements GuiElement, TeamObserver {

	private final Team team;
	
	private final GenericLabel testLabel;
	
	/*
	private class MemberFrame {
		
		private final GenericGradient background;
		private final GenericLabel name;
		
		public MemberFrame (final String name) {
			this.background = new GenericGradient();
			this.name = new GenericLabel(name);
			
		}
		
	}
	*/
	
	public TeamFrame(final Team team) {
		this.team = team;
		
		testLabel = new GenericLabel("test");
		
		team.addTeamObserver(this);
	}
	
	@Override
	public void attach(final Hero hero) {
		hero.getGui().attachWidget(testLabel);
	}

	@Override
	public void detach(final Hero hero) {
		hero.getGui().removeWidget(testLabel);
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
