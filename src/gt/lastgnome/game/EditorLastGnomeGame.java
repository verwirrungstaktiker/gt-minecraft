/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.lastgnome.game;

import gt.general.character.Team;
import gt.general.world.WorldInstance;
import gt.lastgnome.GnomeSocketEnd;
import gt.lastgnome.GnomeSocketStart;

import org.bukkit.entity.Player;

/**
 * A type of LastGnome Game that works with the Editor
 */
public class EditorLastGnomeGame extends AbstractLastGnomeGame {
	
	/**
	 * Instantiate a new LastGnomeGame that works with the Editor
	 */
	public EditorLastGnomeGame() {
		super(new Team());
	}

	@Override
	public void onStartSocketInteract(final Player player) {}

	@Override
	public void onEndSocketInteract(final Player player) {}

	@Override
	public void onEnd() {}
	
	/**
	 * save all TriggerContexts to Yaml
	 */
	public void save() {
		WorldInstance worldInstance = getWorldInstance();
		if (worldInstance.getTriggerManager().canSave()) {
			worldInstance.save();
			worldInstance.saveMeta(GnomeSocketStart.FILENAME, getStartSocket());
			worldInstance.saveMeta(GnomeSocketEnd.FILENAME, getEndSocket());
		} else {
			System.out.println("Cannot Save. There are still unfinished contexts");
		}
		
	}

	@Override
	public void onPause() {
	}

	@Override
	public void onResume() {
	}
}
