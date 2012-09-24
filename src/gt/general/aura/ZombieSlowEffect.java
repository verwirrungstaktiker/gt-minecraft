/*******************************************************************************
 * Projektpraktikum: Game Technology 2012
 * Minecraft-Modifikation fuer kollaboratives Spielen
 * 
 * Sebastian Fahnenschreiber (sebastian.fahnenschreiber@stud.tu-darmstadt.de)
 * Roman Ness (roman.ness@stud.tu-darmstadt.de)
 * Philipp Pascal Battenberg (philipp.battenberg@stud.tu-darmstadt.de)
 ******************************************************************************/
package gt.general.aura;

import gt.general.character.Character;
import gt.general.character.CharacterAttributes;
import gt.general.character.ZombieManager;

public class ZombieSlowEffect extends AbstractEffect {

	private int remainingTicks = ZombieManager.SCHEDULE_RATE;
	
	@Override
	public int getPriorityIndex() {
		return 0;
	}

	@Override
	public void performTick() {
		remainingTicks -= ZombieManager.SCHEDULE_RATE;
	}

	@Override
	public int remainingTicks() {
		return remainingTicks;
	}

	@Override
	public void takeEffect(final Character character) {
		character.addToAttribute(CharacterAttributes.SPEED, -80.0);
	}

}
