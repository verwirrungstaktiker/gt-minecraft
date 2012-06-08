package gt.general.gui;

import gt.general.character.Hero;
import gt.general.character.Hero.Notification;
import gt.general.character.Team;
import gt.general.character.TeamObserver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;

public class TeamFrame implements GuiElement, TeamObserver {

	private final GenericLabel testLabel;
	private final Map<Hero, HeroFrame> frameMapping;
	
	private Hero holder;

	private class HeroFrame {

		private final static int HEIGHT = 15;
		private final static int BASEWIDTH = 75;
		
		private final GenericGradient background;
		private final GenericLabel name;

		public HeroFrame(final Hero hero) {
			background = new GenericGradient();
			
			background
				.setTopColor(new Color(0, 0, 255, 75))
				.setBottomColor(new Color(0, 0, 255))
				.setWidth(BASEWIDTH)
				.setHeight(HEIGHT)
				.setPriority(RenderPriority.Highest);
				
			
			name = new GenericLabel(hero.getPlayer().getName());
			
			name.setAlign(WidgetAnchor.CENTER_LEFT)
				.setWidth(BASEWIDTH)
				.setHeight(HEIGHT)
				.setX(2)
				.setPriority(RenderPriority.Lowest);

		}

		public void layout(int rank) {
			name.setY(HEIGHT * rank + HEIGHT / 2);
			background.setY(HEIGHT * rank);
		}
		
		public void attach(final Hero hero) {
			hero.getGui().attachWidgets(name, background);
		}
		
		public void detach(final Hero hero) {
			hero.getGui().removeWidgets(name, background );
		}

	}

	public TeamFrame(final Team team) {
		holder = null;
		
		testLabel = new GenericLabel("test");
		frameMapping = new HashMap<Hero, TeamFrame.HeroFrame>();

		updateLayout(team);

		team.addTeamObserver(this);
	}

	@Override
	public void attach(final Hero hero) {
		for(HeroFrame heroframe : frameMapping.values()) {
			heroframe.attach(hero);
		}
		holder = hero;
	}

	@Override
	public void detach(final Hero hero) {
		
		holder = null;
		
		for(HeroFrame heroframe : frameMapping.values()) {
			heroframe.detach(hero);
		}
	}
	
	@Override
	public void update(final Hero hero, final Notification notification) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(final Team team,
			final gt.general.character.Team.Notification notification) {

		if (notification == gt.general.character.Team.Notification.MEMBERCHANGE) {
			updateLayout(team);
		}

	}

	/**
	 * Updates the internal mapping and relayouts its contents
	 * 
	 * @param team The Team which is represented by this TeamFrame
	 */
	private void updateLayout(final Team team) {

		// phase 1: check if each label is still valid
		Iterator<Hero> it = frameMapping.keySet().iterator();
		while (it.hasNext()) {
			Hero hero = it.next();
			if (!team.isMember(hero)) {
				
				if(holder != null) {
					frameMapping.get(hero).detach(holder);
				}
				
				hero.removeObserver(this);

				it.remove();
			}
		}

		// phase 2: check if there are new team members and relayout
		int rank = 0;
		for (Hero hero : team.getPlayers()) {

			if (frameMapping.containsKey(hero)) {
				frameMapping.get(hero).layout(rank);
			} else {
				HeroFrame frame = new HeroFrame(hero);
				
				if(holder != null) {
					frame.attach(holder);
				}
				
				frameMapping.put(hero, frame);
				hero.addObserver(this);
				
				frame.layout(rank);
			}

			rank++;
		}
	}
}
