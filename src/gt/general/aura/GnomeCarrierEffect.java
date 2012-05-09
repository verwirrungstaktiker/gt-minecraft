package gt.general.aura;

import gt.general.Hero;
import gt.general.Character;

public class GnomeCarrierEffect implements Effect {

	@Override
	public void takeEffect(Character character) {
				
		if(character instanceof Hero) {
			Hero hero = (Hero)character;
			// lower actual speed
			return;
		}

	}

}
