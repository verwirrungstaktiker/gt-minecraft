package gt.general.gui;

import gt.general.character.Hero;
import gt.general.character.Team;
import gt.general.character.TeamObserver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TeamFrame implements GuiElement, TeamObserver {

	private final Map<Hero, HeroFrame> frameMapping;
	
	private Hero holder;

	/**
	 * @param team The Team, which is tracked by this TeamFrame.
	 */
	public TeamFrame(final Team team) {
		holder = null;
		
		frameMapping = new HashMap<Hero, HeroFrame>();

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
			HeroFrame frame = frameMapping.get(hero);
			
			if (!team.isMember(hero)) {
				
				if(isAttached()) {
					frame.detach(holder);
				}
				
				hero.removeObserver(frame);

				it.remove();
			}
		}

		// phase 2: check if there are new team members and relayout
		int rank = 0;
		for (Hero hero : team.getPlayers()) {

			if (frameMapping.containsKey(hero)) {
				frameMapping.get(hero).layout(rank);
			} else {
				HeroFrame frame = new HeroFrame(this, hero);
				
				if(isAttached()) {
					frame.attach(holder);
				}
				
				frameMapping.put(hero, frame);
				hero.addObserver(frame);
				
				frame.layout(rank);
			}

			rank++;
		}
	}
	
	/**
	 * @return The Hero holding this TeamFrame.
	 */
	public Hero getHolder() {
		return holder;
	}
	
	/**
	 * @return true, if this TeamFrame is attached to a Heros gui.
	 */
	public boolean isAttached() {
		return holder != null;
	}
}
