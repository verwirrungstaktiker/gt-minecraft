package gt.general.gui;

import gt.general.character.Hero;
import gt.general.character.Hero.Notification;
import gt.general.character.Team;
import gt.general.character.TeamObserver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;

public class TeamFrame implements GuiElement, TeamObserver {

	private final GenericLabel testLabel;
	private final Map<Hero, HeroFrame> frameMapping;
	
	private Hero holder;

	private class HeroFrame {

		private final GenericGradient background;
		private final GenericLabel name;

		public HeroFrame(final Hero hero) {
			this.background = new GenericGradient();
			this.name = new GenericLabel(hero.getPlayer().getName());

		}

		public void layout(int rank) {
		}

	}

	public TeamFrame(final Team team) {
		testLabel = new GenericLabel("test");
		frameMapping = new HashMap<Hero, TeamFrame.HeroFrame>();

		updateLayout(team);

		team.addTeamObserver(this);
	}

	@Override
	public void attach(final Hero hero) {
		// TODO
		hero.getGui().attachWidget(testLabel);
	}

	@Override
	public void detach(final Hero hero) {
		// TODO
		hero.getGui().removeWidget(testLabel);
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
				// TODO remove HeroFrame frameMapping.get(hero).detach()
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
				frameMapping.put(hero, frame);
				hero.addObserver(this);
				
				frame.layout(rank);
			}

			rank++;
		}
	}
}
