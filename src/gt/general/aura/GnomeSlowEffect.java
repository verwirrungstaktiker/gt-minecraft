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
import gt.general.character.Hero;
/**
 * Effect for slowing down gnome bearer, and draining his stamina
 */
public class GnomeSlowEffect extends AbstractEffect {
	
	// lowers the speed by 1/16
	public static final double SLOWRATE = 0.0625;
	
	@Override
	public void performTick() {
	}

	@Override
	public int remainingTicks() {
		return 1; // never expire
	}

	@Override
	public void takeEffect(final Character character) {

		// lowers the speed by 1/8
		if(character instanceof Hero) {
			Hero hero = (Hero)character;
			hero.addToAttribute(CharacterAttributes.SPEED,
								hero.getBaseAttribute(CharacterAttributes.SPEED) * -SLOWRATE);
		}
	}

	@Override
	public int getPriorityIndex() {
		return 0;
	}



}
