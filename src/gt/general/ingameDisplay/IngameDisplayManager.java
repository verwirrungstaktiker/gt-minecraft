package gt.general.ingameDisplay;

import static com.google.common.collect.Sets.*;
import gt.general.character.Hero;
import gt.general.character.HeroManager;

import java.util.Set;

public class IngameDisplayManager implements Runnable{
	
	public static final int REFRESH_RATE = 20 * 5;

	private final Set<DisplayString> strings = newHashSet();
	
	@Override
	public void run() {
		for(Hero hero : HeroManager.getAllHeros()) {
		
			for(DisplayString string : strings) {
			
				if(hero.getPlayer().getWorld() == string.getLocation().getWorld()) {
					string.spawnForPlayer(hero.getSpoutPlayer());
				}
				
			}
		}
	}
	
	public void add(final DisplayString displayString) {
		strings.add(displayString);
	}

}
