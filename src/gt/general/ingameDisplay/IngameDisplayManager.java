/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.ingameDisplay;

import static com.google.common.collect.Sets.newHashSet;
import gt.general.character.Hero;
import gt.general.character.HeroManager;

import java.util.Set;

public class IngameDisplayManager implements Runnable{
	
	public static final int REFRESH_RATE = 20 * 15;

	private final Set<DisplayStringContainer> containers = newHashSet();
	
	
	@Override
	public void run() {
		
		if(HeroManager.getAllHeros().size() > 0) {
		
			for(DisplayStringContainer container : containers) {
				for(DisplayString string : container.getDisplayStrings()) {
					for(Hero hero : HeroManager.getAllHeros()) {
						
						if(hero.getPlayer().getWorld() == string.getLocation().getWorld()) {
							string.spawnForPlayer(hero.getSpoutPlayer());
						}
						
					}
				}
			}
		}
	}
	
	/**
	 * add a container that itself contains DisplayStrings
	 * @param container the Container to be added
	 */
	public void add(final DisplayStringContainer container) {
		containers.add(container);
	}
}
