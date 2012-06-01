package gt.general.aura;

import gt.general.character.Character;
import gt.general.character.CharacterAttributes;
import gt.general.character.Hero;
/**
 * Effect for slowing down gnome bearer, and draining his stamina
 */
public class GnomeCarrierEffect implements Effect {

	@Override
	public void performTick() {
	}

	@Override
	public int remainingTicks() {
		return 1; // never expire
	}

	@Override
	public void takeEffect(final Character character) {
		
		if(character instanceof Hero) {
			Hero hero = (Hero)character;

			hero.scaleAttribute(CharacterAttributes.JUMPMULTIPLIER, 0.0);
			hero.addToAttribute(CharacterAttributes.JUMPMULTIPLIER, -1);
		}

	}



}
